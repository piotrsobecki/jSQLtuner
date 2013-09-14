package pl.piotrsukiennik.tuner.datasources.shard;

import org.springframework.stereotype.Component;
import pl.piotrsukiennik.tuner.cache.CacheManager;
import pl.piotrsukiennik.tuner.datasources.DataRetrieval;
import pl.piotrsukiennik.tuner.datasources.IDataSource;
import pl.piotrsukiennik.tuner.persistance.model.query.Query;
import pl.piotrsukiennik.tuner.persistance.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.persistance.model.query.SelectQuery;
import pl.piotrsukiennik.tuner.persistance.service.QueryExecutionServiceWrapper;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Author: Piotr Sukiennik
 * Date: 31.08.13
 * Time: 18:58
 */
@Component
public class ShardingManager implements IShardingManager{

    private @Resource List<IDataSharder> dataSharders;
    private @Resource QueryExecutionServiceWrapper queryExecutionService;
    private @Resource  CacheManager cacheManager;


    @Override
    public DataRetrieval getData(ReadQuery query) {
        IDataSource dataSource =  queryExecutionService.getDataSourceForQuery(query);
        if (dataSource!=null) {
            try{
                DataRetrieval dataRetrieval = null;
                dataRetrieval = dataSource.get(query);
                dataRetrieval.setDataSource(dataSource);
                return dataRetrieval;
            }catch (Throwable t){
                t.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void put(ReadQuery query, Serializable data) {
        for (IDataSharder dataSharder : dataSharders) {
            dataSharder.put(query, data);
            queryExecutionService.submitPossibleDataSource(query,dataSharder);
        }
        if (query instanceof SelectQuery){
            cacheManager.putCachedQuery((SelectQuery)query);
        }
    }

    @Override
    public void delete(Query query) {
        Collection<SelectQuery> queriesToInvalidate =  cacheManager.getQueriesToInvalidate(query);
        for (IDataSharder dataSharder : dataSharders) {
            if (dataSharder instanceof KeyValueDataSharder){
                for (SelectQuery selectQuery: queriesToInvalidate){
                    dataSharder.delete(selectQuery);
                }
            } else {
                dataSharder.delete(query);
            }
        }
        queryExecutionService.removePossibleDataSources(queriesToInvalidate,dataSharders);
    }

}
