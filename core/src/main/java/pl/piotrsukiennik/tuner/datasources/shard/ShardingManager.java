package pl.piotrsukiennik.tuner.datasources.shard;

import org.springframework.stereotype.Component;
import pl.piotrsukiennik.tuner.datasources.DataRetrieval;
import pl.piotrsukiennik.tuner.datasources.IDataSource;
import pl.piotrsukiennik.tuner.persistance.model.query.Query;
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
    public DataRetrieval getData(SelectQuery query) {
        IDataSource dataSource =  queryExecutionService.getDataSourceForQuery(query);
        if (dataSource!=null) {
            try{
                ResultSet resultSet = null;
                DataRetrieval dataRetrieval = new DataRetrieval();
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
    public void put(SelectQuery query, Serializable data) {
        for (IDataSharder dataSharder : dataSharders) {
            dataSharder.putData(query, data);
        }
    }

    @Override
    public void delete(Query query) {
        for (IDataSharder dataSharder : dataSharders) {
            dataSharder.delete(query);
        }
    }

}
