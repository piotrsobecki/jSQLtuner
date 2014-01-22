package pl.piotrsukiennik.tuner.datasources;

import org.springframework.stereotype.Component;
import pl.piotrsukiennik.tuner.IDataSource;
import pl.piotrsukiennik.tuner.IShardingManager;
import pl.piotrsukiennik.tuner.dto.DataRetrieval;
import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.model.query.SelectQuery;
import pl.piotrsukiennik.tuner.model.query.execution.QueryForDataSource;
import pl.piotrsukiennik.tuner.service.LocalDataSourceService;
import pl.piotrsukiennik.tuner.service.QueryExecutionService;
import pl.piotrsukiennik.tuner.utils.RowSet;

import javax.annotation.Resource;
import javax.sql.rowset.CachedRowSet;
import java.sql.ResultSet;
import java.util.Collection;

/**
 * Author: Piotr Sukiennik
 * Date: 26.08.13
 * Time: 22:03
 */
@Component
public class DataSourcesManager {

    private
    @Resource
    LocalDataSourceService dataSourceMapper;

    private
    @Resource
    IShardingManager shardingManager;

    private
    @Resource
    QueryExecutionService executionService;


    public void setDataForQuery( ReadQuery query, IDataSource dataSource ) {
        dataSourceMapper.setRootDataSource( query, dataSource );
    }

    public ResultSet getData( ReadQuery query ) throws Throwable {
        DataRetrieval dataRetrieval = shardingManager.getData( query );
        if ( dataRetrieval == null || dataRetrieval.getResultSet() == null ) {
            IDataSource dataSource = dataSourceMapper.getRootDataSource( query );
            dataRetrieval = dataSource.get( query );
            dataRetrieval.setDataSourceIdentifier( dataSource.getMetaData().getIdentifier() );
            if ( isShardable( query ) &&
             dataRetrieval.getResultSet() != null ) {
                CachedRowSet cachedRowSet = RowSet.cached( dataRetrieval.getResultSet() );
                dataRetrieval.setResultSet( cachedRowSet );
                query.setRows( cachedRowSet.size() );
                shardingManager.put( query, cachedRowSet );
            }
        }
        Collection<QueryForDataSource> queries = executionService.submit( query, dataRetrieval );
        return dataRetrieval.getResultSet();
    }

    public void invalidate( Query query ) {
        shardingManager.delete( query );
    }

    public boolean isShardable( Query query ) {
        return query instanceof SelectQuery;
    }
}
