package pl.piotrsukiennik.tuner.datasources;

import org.springframework.stereotype.Component;
import pl.piotrsukiennik.tuner.datasources.shard.IShardingManager;
import pl.piotrsukiennik.tuner.persistance.model.query.Query;
import pl.piotrsukiennik.tuner.persistance.model.query.SelectQuery;
import pl.piotrsukiennik.tuner.persistance.model.query.execution.QueryForDataSource;
import pl.piotrsukiennik.tuner.persistance.service.QueryExecutionServiceWrapper;
import pl.piotrsukiennik.tuner.util.RowSet;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Author: Piotr Sukiennik
 * Date: 26.08.13
 * Time: 22:03
 */
@Component
public class DataSourcesManager {


    private Map<Query,IDataSource> sources = new LinkedHashMap<Query,IDataSource>();
    private @Resource IShardingManager shardingManager;
    private @Resource QueryExecutionServiceWrapper executionService;

    public void setDataForQuery(Query query, IDataSource dataSource){
        sources.put(query, dataSource);
    }

    public ResultSet getData(SelectQuery query) throws Throwable{
        DataRetrieval dataRetrieval = shardingManager.getData(query);
        if (dataRetrieval == null){
            IDataSource dataSource =  sources.get(query);
            dataRetrieval=dataSource.getData(query);
            dataRetrieval.setDataSource(dataSource);
            if (isShardable(query)){
                shardingManager.put(query, RowSet.cached(dataRetrieval.getResultSet()));
            }
        }
        Collection<QueryForDataSource> queries = executionService.submit(query,dataRetrieval);
        return dataRetrieval.getResultSet();
    }

    public boolean isShardable(Query query){
        return query instanceof SelectQuery;
    }
}
