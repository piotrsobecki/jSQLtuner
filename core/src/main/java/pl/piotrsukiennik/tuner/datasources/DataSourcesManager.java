package pl.piotrsukiennik.tuner.datasources;

import com.sun.rowset.CachedRowSetImpl;
import org.springframework.stereotype.Component;
import pl.piotrsukiennik.tuner.datasources.shard.IShardingManager;
import pl.piotrsukiennik.tuner.persistance.model.query.Query;
import pl.piotrsukiennik.tuner.persistance.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.persistance.model.query.SelectQuery;
import pl.piotrsukiennik.tuner.persistance.model.query.execution.QueryForDataSource;
import pl.piotrsukiennik.tuner.persistance.service.QueryExecutionServiceWrapper;
import pl.piotrsukiennik.tuner.statement.LocalDataSourceMapper;
import pl.piotrsukiennik.tuner.util.RowSet;
import pl.piotrsukiennik.tuner.util.holder.ServicesHolder;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.util.Collection;

/**
 * Author: Piotr Sukiennik
 * Date: 26.08.13
 * Time: 22:03
 */
@Component
public class DataSourcesManager {

    private @Resource
    LocalDataSourceMapper dataSourceMapper;

    private @Resource IShardingManager shardingManager;
    private @Resource QueryExecutionServiceWrapper executionService;
    private @Resource ServicesHolder servicesHolder;
    public void setDataForQuery(ReadQuery query, IDataSource dataSource){
        dataSourceMapper.setRootDataSource(query,dataSource);
    }

    public ResultSet getData(ReadQuery query) throws Throwable{
        DataRetrieval dataRetrieval = shardingManager.getData(query);
        if (dataRetrieval == null){
            IDataSource dataSource =  dataSourceMapper.getRootDataSource(query);
            dataRetrieval=dataSource.get(query);
            dataRetrieval.setDataSource(dataSource);
            if (isShardable(query) &&
                    dataRetrieval.getResultSet() !=null){
                CachedRowSetImpl cachedRowSet = RowSet.cached(dataRetrieval.getResultSet());
                query.setRows(RowSet.size(cachedRowSet));
                shardingManager.put(query,cachedRowSet);
            }
        }
        Collection<QueryForDataSource> queries = executionService.submit(query,dataRetrieval);
        return dataRetrieval.getResultSet();
    }

    public boolean isShardable(Query query){
        return query instanceof SelectQuery;
    }
}
