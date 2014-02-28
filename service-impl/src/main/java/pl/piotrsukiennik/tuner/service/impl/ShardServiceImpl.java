package pl.piotrsukiennik.tuner.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.piotrsukiennik.tuner.DataSource;
import pl.piotrsukiennik.tuner.LoggableService;
import pl.piotrsukiennik.tuner.ShardNode;
import pl.piotrsukiennik.tuner.ShardService;
import pl.piotrsukiennik.tuner.dto.DataRetrieval;
import pl.piotrsukiennik.tuner.exception.DataRetrievalException;
import pl.piotrsukiennik.tuner.model.datasource.DataSourceIdentity;
import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.service.DataRetrievalService;
import pl.piotrsukiennik.tuner.service.DataSourceSelector;
import pl.piotrsukiennik.tuner.service.DataSourceService;
import pl.piotrsukiennik.tuner.service.QueryInvalidatorService;
import pl.piotrsukiennik.tuner.util.RowSet;

import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

/**
 * Author: Piotr Sukiennik
 * Date: 31.08.13
 * Time: 18:58
 */
@Service
public class ShardServiceImpl implements ShardService {

    @Autowired
    private List<ShardNode> nodes;

    @Autowired
    private DataSourceService dataSourceService;

    @Autowired
    private DataRetrievalService dataRetrievalService;

    @Autowired
    private QueryInvalidatorService invalidatorService;

    @Autowired
    private DataSourceSelector dataSourceSelector;

    @Autowired
    private LoggableService loggableService;

    @Override
    public boolean contains( ReadQuery query ) {
        for ( ShardNode shardNode : nodes ) {
            if ( shardNode.contains( query ) ) {
                return true;
            }
        }
        return false;
    }

    @Override
    public DataRetrieval get( ReadQuery query ) throws DataRetrievalException {
        DataSource dataSource;
        DataRetrieval dataRetrieval = null;
        DataSourceIdentity dataSourceIdentity = dataSourceSelector.getDataSource( query );
        if ( dataSourceIdentity != null ) {
            dataSource = dataSourceService.getDataSource( query, dataSourceIdentity );
            if ( dataSource != null ) {
                try {
                    dataRetrieval = dataRetrievalService.get( dataSource, query );
                }
                catch ( DataRetrievalException dre ) {
                    dre.accept( loggableService );
                }
            }
        }
        if ( dataRetrieval == null || dataRetrieval.getResultSet() == null ) {
            dataSource = dataSourceService.getDefault( query );
            dataRetrieval = getRootData( dataSource, query );
        }
        loggableService.log( dataRetrieval );
        dataSourceSelector.submitDataRetrieval( dataRetrieval );
        return dataRetrieval;
    }

    protected DataRetrieval getRootData( DataSource dataSource, ReadQuery query ) throws DataRetrievalException {
        DataRetrieval dataRetrieval = dataRetrievalService.get( dataSource, query );
        try {
            CachedRowSet cachedRowSet = RowSet.cached( dataRetrieval.getResultSet() );
            dataRetrieval.setResultSet( cachedRowSet );
            query.setRows( cachedRowSet.size() );
            put( query, cachedRowSet );
        }
        catch ( SQLException e ) {
            throw new DataRetrievalException( "Data Sources Manager could cache ResultSet", e, query, dataSource.getDataSourceIdentity() );
        }
        return dataRetrieval;
    }


    @Override
    public void put( ReadQuery query, CachedRowSet data ) {
        CachedRowSet clonedData = RowSet.clone( data );
        for ( ShardNode node : nodes ) {
            node.put( query, clonedData );
            dataSourceSelector.scheduleDataRetrieval( query, node.getDataSourceIdentity() );
        }
        invalidatorService.putCachedQuery( query );
    }

    @Override
    public void delete( Query query ) {
        Collection<ReadQuery> queriesToInvalidate = query.invalidates( invalidatorService );
        for ( ShardNode node : nodes ) {
            node.delete( query, queriesToInvalidate );
        }
        for ( ReadQuery selectQuery : queriesToInvalidate ) {
            dataSourceSelector.removeDataSources( selectQuery );
        }
    }

    public void setDefaultDataSource( ReadQuery query, DataSource dataSource ) {
        dataSourceService.setDefault( query, dataSource );
    }
}
