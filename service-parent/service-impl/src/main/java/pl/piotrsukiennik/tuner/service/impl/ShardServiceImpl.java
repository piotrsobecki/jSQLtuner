package pl.piotrsukiennik.tuner.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.piotrsukiennik.tuner.LoggableService;
import pl.piotrsukiennik.tuner.ShardNode;
import pl.piotrsukiennik.tuner.dto.DataRetrieval;
import pl.piotrsukiennik.tuner.exception.DataRetrievalException;
import pl.piotrsukiennik.tuner.model.datasource.DataSourceIdentity;
import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.service.DataSourceSelector;
import pl.piotrsukiennik.tuner.service.QueryInvalidatonService;
import pl.piotrsukiennik.tuner.util.RowSet;

import javax.sql.rowset.CachedRowSet;
import java.util.Collection;
import java.util.Collections;

/**
 * Author: Piotr Sukiennik
 * Date: 31.08.13
 * Time: 18:58
 */
@Service
public class ShardServiceImpl extends AbstractShardServiceImpl {

    @Autowired
    private QueryInvalidatonService invalidatorService;

    @Autowired
    private DataSourceSelector dataSourceSelector;

    @Autowired
    private LoggableService loggableService;

    @Override
    public boolean contains( ReadQuery query ) {
        return !dataSourceSelector.supporting( query ).isEmpty();
    }

    @Override
    public DataRetrieval get( ReadQuery query ) throws DataRetrievalException {
        DataRetrieval dataRetrieval = null;
        DataSourceIdentity dataSourceIdentity = dataSourceSelector.select( query );
        if ( dataSourceIdentity != null ) {
            try {
                //Get using selected data source identity by the selector
                dataRetrieval = getUsingDataSource( dataSourceIdentity,query );
            }
            catch ( DataRetrievalException dre ) {
                dre.accept( loggableService );
            }
        }
        //No data retreived
        if ( dataRetrieval == null || dataRetrieval.getResultSet() == null ) {
            //Retreive using root database
            dataRetrieval = getUsingDefault( query );
            //Propagate data
            put( query, dataRetrieval );
        }
        //Log retrieval
        loggableService.log( dataRetrieval );
        //Feedback retrieval to data source selector for learning
        dataSourceSelector.submit( dataRetrieval );
        return dataRetrieval;
    }


    @Override
    public void put( ReadQuery query, DataRetrieval data ) {
        //Clone data for nodes
        CachedRowSet clonedData = RowSet.clone( data.getResultSet() );
        //Get possible nodes for sharding
        Collection<ShardNode> selectedNodes = dataSourceSelector.possible(
             nodes,
             query,
             data
        );
        for ( ShardNode node : selectedNodes ) {
            //Distribute data for each node
            node.put( query, clonedData );
            //Schedule the selection using the node
            dataSourceSelector.schedule( query, node );
        }
        //Inform invalidator service about new supported query
        invalidatorService.putCachedQuery( query );
    }

    @Override
    public void delete( Query query ) {
        //Get read queries that this query invalidates
        Collection<ReadQuery> queriesToInvalidate = query.invalidates( invalidatorService );
        //Remove option of selection
        for ( ReadQuery selectQuery : queriesToInvalidate ) {
            dataSourceSelector.removeOptions( selectQuery );
        }
        for (ReadQuery readQuery: queriesToInvalidate){
            //Get nodes on which this query is distributed
            Collection<DataSourceIdentity> selectedNodes = dataSourceSelector.supporting( readQuery );
            for (DataSourceIdentity dataSourceIdentity: selectedNodes){
                //DInvalidate queries for each node
                ShardNode shardNode = getShardNode( dataSourceIdentity );
                if (shardNode!=null){
                    shardNode.delete( query,queriesToInvalidate );
                }
            }
        }
    }

}
