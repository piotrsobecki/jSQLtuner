package pl.piotrsukiennik.tuner.service.impl;

import com.google.common.base.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import pl.piotrsukiennik.tuner.DataSource;
import pl.piotrsukiennik.tuner.LoggableService;
import pl.piotrsukiennik.tuner.ShardNode;
import pl.piotrsukiennik.tuner.ShardService;
import pl.piotrsukiennik.tuner.dto.ReadQueryExecutionResult;
import pl.piotrsukiennik.tuner.exception.DataRetrievalException;
import pl.piotrsukiennik.tuner.model.datasource.DataSourceIdentity;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.service.DataRetrievalService;
import pl.piotrsukiennik.tuner.service.DataSourceService;
import pl.piotrsukiennik.tuner.util.RowSet;

import javax.sql.rowset.CachedRowSet;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

/**
 * @author Piotr Sukiennik
 * @date 22.05.14
 */
public abstract class AbstractShardServiceImpl implements ShardService {
    protected Collection<ShardNode> nodes;

    @Autowired
    private DataSourceService dataSourceService;

    @Autowired
    private DataRetrievalService dataRetrievalService;

    @Autowired
    private LoggableService loggableService;

    protected ShardNode getShardNode(DataSourceIdentity dataSourceIdentity){
        for (ShardNode shardNode:nodes){
            if ( Objects.equal( dataSourceIdentity, shardNode.getDataSourceIdentity() ) ){
                return shardNode;
            }
        }
        return null;
    }

    protected ReadQueryExecutionResult getUsingDefault(  ReadQuery query ) throws DataRetrievalException {
        DataSource dataSource =  dataSourceService.getDataSourceDefault( query );
        return getUsingDataSource( dataSource, query );
    }

    protected ReadQueryExecutionResult getUsingDataSource( DataSourceIdentity dataSourceIdentity, ReadQuery query ) throws DataRetrievalException {
        DataSource dataSource =dataSourceService.getDataSource( query, dataSourceIdentity );
        if (dataSource!=null){
            return getUsingDataSource( dataSource,query );
        }
        return null;
    }

    protected ReadQueryExecutionResult getUsingDataSource( DataSource dataSource, ReadQuery query ) throws DataRetrievalException {
        ReadQueryExecutionResult readQueryExecutionResult = dataRetrievalService.get( dataSource, query );
        CachedRowSet cachedRowSet = RowSet.clone( readQueryExecutionResult.getResultSet() );
        readQueryExecutionResult.setResultSet( cachedRowSet );
        query.setRows( cachedRowSet.size() );
        return readQueryExecutionResult;
    }

    protected Collection<ShardNode> minus(Collection<ShardNode> shardNodes, Collection<DataSourceIdentity> dataSourceIdentities){
        //Prepare output list
        Collection<ShardNode> newNodes = new LinkedList<>(  );
        //filter nodes
        for (ShardNode shardNode: shardNodes){
            //If no data source identity in identities list
            if (!dataSourceIdentities.contains( shardNode.getDataSourceIdentity())){
                //Add node to output
                newNodes.add( shardNode );
            }
        }
        return newNodes;
    }
    protected void log( DataRetrievalException exception ){
        loggableService.log( exception );
    }

    protected void log( ReadQueryExecutionResult data ){
        loggableService.log( data );
    }

    @Autowired
    public void setNodes( Collection<ShardNode> nodes ) {
        this.nodes = Collections.unmodifiableCollection( nodes );
    }


    public void setDefaultDataSource( ReadQuery query, DataSource dataSource ) {
        dataSourceService.setDefault( query, dataSource );
    }
}
