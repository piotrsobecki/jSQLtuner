package pl.piotrsukiennik.tuner.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.piotrsukiennik.tuner.DataSource;
import pl.piotrsukiennik.tuner.KeyValueSharderNode;
import pl.piotrsukiennik.tuner.Sharder;
import pl.piotrsukiennik.tuner.SharderNode;
import pl.piotrsukiennik.tuner.dto.DataRetrieval;
import pl.piotrsukiennik.tuner.exception.DataRetrievalException;
import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.model.query.SelectQuery;
import pl.piotrsukiennik.tuner.model.query.datasource.DataSourceIdentity;
import pl.piotrsukiennik.tuner.service.CacheManagerService;
import pl.piotrsukiennik.tuner.service.DataSourceSelector;
import pl.piotrsukiennik.tuner.service.LocalDataSourceService;
import pl.piotrsukiennik.tuner.service.util.RowSet;

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
public class SharderImpl implements Sharder {


    private static final Log LOG = LogFactory.getLog( SharderImpl.class );

    @Autowired
    private
    List<SharderNode> sharderNodes;

    @Autowired
    private LocalDataSourceService dataSourceMapper;

    @Autowired
    private CacheManagerService cacheManagerService;

    @Autowired
    private DataSourceSelector dataSourceSelector;

    @Override
    public boolean contains( ReadQuery query ) {
        for ( SharderNode sharderNode : sharderNodes ) {
            if ( sharderNode.contains( query ) ) {
                return true;
            }
        }
        return false;
    }

    @Override
    public DataRetrieval get( ReadQuery query ) throws DataRetrievalException {
        DataRetrieval dataRetrieval = null;
        DataSource dataSource = null;
        DataSourceIdentity dataSourceIdentity = dataSourceSelector.selectDataSourceForQuery( query );
        if ( dataSourceIdentity != null ) {
            dataSource = dataSourceMapper.getSingleLocal( query, dataSourceIdentity );
        }
        if ( dataSource != null ) {
            try {
                dataRetrieval = dataSource.get( query );
                dataRetrieval.setDataSourceIdentifier( dataSource.getDataSourceIdentity().getIdentifier() );
            }
            catch ( DataRetrievalException dre ) {
                if ( LOG.isWarnEnabled() ) {
                    LOG.warn( "No data could be retreived using sharding manager.", dre );
                }
            }
        }
        if ( dataRetrieval == null || dataRetrieval.getResultSet() == null ) {
            dataSource = dataSourceMapper.getRootDataSource( query );
            dataSourceIdentity = dataSource.getDataSourceIdentity();
            dataRetrieval = getRootData( query, dataSource );
        }
        if ( LOG.isInfoEnabled() ) {
            LOG.info( String.format( "Retreived Data for (%s) using dataSource (%s)", query.getValue(), dataRetrieval.getDataSourceIdentifier() ) );
        }
        dataSourceSelector.submitDataRetrieval( dataSourceIdentity, query, dataRetrieval );
        return dataRetrieval;
    }

    protected DataRetrieval getRootData( ReadQuery query, DataSource dataSource ) throws DataRetrievalException {
        DataRetrieval dataRetrieval = dataSource.get( query );
        dataRetrieval.setDataSourceIdentifier( dataSource.getDataSourceIdentity().getIdentifier() );
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
        DataSource dataSource = dataSourceMapper.getRootDataSource( query );
        return getRootData( query, dataSource );
    }

    @Override
    public void put( ReadQuery query, CachedRowSet data ) {
        CachedRowSet clonedData = RowSet.clone( data );
        for ( SharderNode sharderNode : sharderNodes ) {
            sharderNode.put( query, clonedData );
            dataSourceSelector.scheduleDataRetrieval( sharderNode.getDataSourceIdentity(), query );
        }
        if ( query instanceof SelectQuery ) {
            cacheManagerService.putCachedQuery( (SelectQuery) query );
        }
    }

    @Override
    public void delete( Query query ) {
        Collection<SelectQuery> queriesToInvalidate = cacheManagerService.getQueriesToInvalidate( query );
        for ( SharderNode sharderNode : sharderNodes ) {
            if ( sharderNode instanceof KeyValueSharderNode ) {
                for ( SelectQuery selectQuery : queriesToInvalidate ) {
                    sharderNode.delete( selectQuery );
                }
            }
            else {
                sharderNode.delete( query );
            }
        }
        for ( SelectQuery selectQuery : queriesToInvalidate ) {
            dataSourceSelector.removeDataSourcesForQuery( selectQuery );
        }
    }

    public void setRootDataForQuery( ReadQuery query, DataSource dataSource ) {
        dataSourceMapper.setRootDataSource( query, dataSource );
    }
}
