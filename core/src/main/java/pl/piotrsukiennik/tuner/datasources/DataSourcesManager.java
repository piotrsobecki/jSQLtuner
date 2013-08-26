package pl.piotrsukiennik.tuner.datasources;

import org.springframework.stereotype.Component;
import pl.piotrsukiennik.tuner.persistance.model.query.Query;

import java.sql.ResultSet;
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


    public void setDataForQuery(Query query, IDataSource dataSource){
        sources.put(query, dataSource);
    }



    public ResultSet getData(Query query) throws Throwable{
        IDataSource dataSource =  sources.get(query);
        return  dataSource.getData(query);
    }


}
