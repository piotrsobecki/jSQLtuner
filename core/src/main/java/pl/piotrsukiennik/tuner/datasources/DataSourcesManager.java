package pl.piotrsukiennik.tuner.datasources;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import pl.piotrsukiennik.tuner.DataSource;
import pl.piotrsukiennik.tuner.Sharder;
import pl.piotrsukiennik.tuner.dto.DataRetrieval;
import pl.piotrsukiennik.tuner.exception.DataRetrievalException;
import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.model.query.SelectQuery;
import pl.piotrsukiennik.tuner.service.LocalDataSourceService;
import pl.piotrsukiennik.tuner.service.QueryDataSourceSelectorService;
import pl.piotrsukiennik.tuner.utils.RowSet;

import javax.annotation.Resource;
import javax.sql.rowset.CachedRowSet;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Author: Piotr Sukiennik
 * Date: 26.08.13
 * Time: 22:03
 */
@Component
public class DataSourcesManager {

    private static final Log LOG = LogFactory.getLog( DataSourcesManager.class );

    @Resource
    private
    LocalDataSourceService dataSourceMapper;

    @Resource
    private
    Sharder shardingManager;

    @Resource
    private
    QueryDataSourceSelectorService executionService;


    public void setDataForQuery( ReadQuery query, DataSource dataSource ) {
        dataSourceMapper.setRootDataSource( query, dataSource );
    }

    public ResultSet getData( ReadQuery query ) throws DataRetrievalException {
        DataRetrieval dataRetrieval = null;
        try {
            dataRetrieval = shardingManager.getData( query );
        }
        catch ( DataRetrievalException t ) {
            if ( LOG.isWarnEnabled() ) {
                LOG.warn( "No data could be retreived using sharding manager.", t );
            }
            DataSource dataSource = dataSourceMapper.getRootDataSource( query );
            dataRetrieval = dataSource.get( query );
            dataRetrieval.setDataSourceIdentifier( dataSource.getMetaData().getIdentifier() );
            if ( isShardable( query ) && dataRetrieval.getResultSet() != null ) {
                try {
                    CachedRowSet cachedRowSet = RowSet.cached( dataRetrieval.getResultSet() );
                    dataRetrieval.setResultSet( cachedRowSet );
                    query.setRows( cachedRowSet.size() );
                    shardingManager.put( query, cachedRowSet );
                }
                catch ( SQLException e ) {
                    throw new DataRetrievalException( "Data Sources Manager could cache ResultSet", e );
                }
            }
        }
        if ( dataRetrieval == null ) {
            throw new DataRetrievalException( "Data Sources Manager could not obtain ResultSet" );
        }
        else {
            executionService.submit( query, dataRetrieval );
        }
        return dataRetrieval.getResultSet();
    }

    public void invalidate( Query query ) {
        shardingManager.delete( query );
    }

    public boolean isShardable( Query query ) {
        return query instanceof SelectQuery;
    }
}
