package pl.piotrsukiennik.tuner.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.piotrsukiennik.tuner.*;
import pl.piotrsukiennik.tuner.dto.DataRetrieval;
import pl.piotrsukiennik.tuner.exception.DataRetrievalException;
import pl.piotrsukiennik.tuner.model.datasource.DataSourceIdentity;
import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.model.query.impl.SelectQuery;
import pl.piotrsukiennik.tuner.service.CacheService;
import pl.piotrsukiennik.tuner.service.DataRetrievalService;
import pl.piotrsukiennik.tuner.service.DataSourceSelector;
import pl.piotrsukiennik.tuner.service.DataSourceService;
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
    private CacheService cacheService;

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
            dataSource = dataSourceService.getDataSourceByIdentity( query, dataSourceIdentity );
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
            dataSource = dataSourceService.getRootDataSource( query );
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
        if ( query instanceof SelectQuery ) {
            cacheService.putCachedQuery( (SelectQuery) query );
        }
    }

    @Override
    public void delete( Query query ) {
        Collection<SelectQuery> queriesToInvalidate = cacheService.getQueriesToInvalidate( query );
        for ( ShardNode node : nodes ) {
            if ( node instanceof KeyValueShardNode ) {
                for ( SelectQuery selectQuery : queriesToInvalidate ) {
                    node.delete( selectQuery );
                }
            }
            else {
                node.delete( query );
            }
        }
        for ( SelectQuery selectQuery : queriesToInvalidate ) {
            dataSourceSelector.removeDataSources( selectQuery );
        }
    }

    public void setRootDataForQuery( ReadQuery query, DataSource dataSource ) {
        dataSourceService.setRootDataSource( query, dataSource );
    }
}
