package pl.piotrsukiennik.tuner;

import pl.piotrsukiennik.tuner.model.datasource.DataSourceIdentity;

import java.util.Collection;

/**
 * @author Piotr Sukiennik
 * @date 23.05.14
 */
public class DataSources {
    private DataSources(){
    }

    public static DataSource filterByIdentity(Collection<DataSource> dataSources, DataSourceIdentity identity){
        for (DataSource dataSource:dataSources){
            if (dataSource.getDataSourceIdentity().equals( identity )){
                return dataSource;
            }
        }
        return null;
    }
}
