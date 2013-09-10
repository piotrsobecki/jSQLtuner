package pl.piotrsukiennik.tuner.datasources.shard;

import org.springframework.stereotype.Component;
import pl.piotrsukiennik.tuner.datasources.DataRetrieval;
import pl.piotrsukiennik.tuner.datasources.IDataSource;
import pl.piotrsukiennik.tuner.persistance.model.query.Query;
import pl.piotrsukiennik.tuner.persistance.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.persistance.model.query.SelectQuery;
import pl.piotrsukiennik.tuner.persistance.service.QueryExecutionServiceWrapper;

import javax.annotation.Resource;
import javax.sql.rowset.CachedRowSet;
import java.io.Serializable;
import java.sql.ResultSet;
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


    @Override
    public DataRetrieval getData(ReadQuery query) {
        IDataSource dataSource =  queryExecutionService.getDataSourceForQuery(query);
        if (dataSource!=null) {
            try{
                DataRetrieval dataRetrieval = null;
                dataRetrieval = dataSource.getData(query);
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
            dataSharder.putData(query, data);
            queryExecutionService.submitPossibleDataSource(query,dataSharder);
        }
    }

    @Override
    public void delete(Query query) {
        for (IDataSharder dataSharder : dataSharders) {
            dataSharder.delete(query);
            if (query instanceof SelectQuery){
                queryExecutionService.removePossibleDataSource((SelectQuery)query,dataSharder);
            }
        }
    }

}
