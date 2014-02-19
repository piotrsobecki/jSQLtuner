package pl.piotrsukiennik.tuner.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.piotrsukiennik.tuner.DataSource;
import pl.piotrsukiennik.tuner.KeyValueShardNode;
import pl.piotrsukiennik.tuner.ShardNode;
import pl.piotrsukiennik.tuner.ShardService;
import pl.piotrsukiennik.tuner.dto.DataRetrieval;
import pl.piotrsukiennik.tuner.exception.DataRetrievalException;
import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.model.query.SelectQuery;
import pl.piotrsukiennik.tuner.model.query.datasource.DataSourceIdentity;
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


    private static final Log LOG = LogFactory.getLog( ShardServiceImpl.class );

    @Autowired
    private
    List<ShardNode> nodes;

    @Autowired
    private DataSourceService dataSourceService;

    @Autowired
    private DataRetrievalService dataRetrievalService;

    @Autowired
    private CacheService cacheService;

    @Autowired
    private DataSourceSelector dataSourceSelector;

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
        DataRetrieval dataRetrieval = null;
        DataSource dataSource = null;
        DataSourceIdentity dataSourceIdentity = dataSourceSelector.getDataSource( query );
        if ( dataSourceIdentity != null ) {
            dataSource = dataSourceService.getDataSourceByIdentity( query, dataSourceIdentity );
        }
        if ( dataSource != null ) {
            try {
                dataRetrieval = dataRetrievalService.get( dataSource, query );
            }
            catch ( DataRetrievalException dre ) {
                if ( LOG.isWarnEnabled() ) {
                    LOG.warn( "No data could be retreived using sharding manager.", dre );
                }
            }
        }
        if ( dataRetrieval == null || dataRetrieval.getResultSet() == null ) {
            dataSource = dataSourceService.getRootDataSource( query );
            dataRetrieval = getRootData( dataSource, query );
            dataSourceIdentity = dataSource.getDataSourceIdentity();
        }
        if ( LOG.isInfoEnabled() ) {
            LOG.info( String.format( "Execute (%s) on (%s:%s)", query.getValue(), dataSourceIdentity.getClazz(), dataSourceIdentity.getIdentifier() ) );
        }
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
            throw new DataRetrievalException( "Data Sources Manager could cache ResultSet", e );
        }
        return dataRetrieval;
    }

    public DataRetrieval getRootData( ReadQuery query ) throws DataRetrievalException {
        DataSource dataSource = dataSourceService.getRootDataSource( query );
        return getRootData( dataSource, query );
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
