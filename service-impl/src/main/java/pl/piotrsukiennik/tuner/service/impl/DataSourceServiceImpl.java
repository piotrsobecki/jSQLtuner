package pl.piotrsukiennik.tuner.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.piotrsukiennik.tuner.DataSource;
import pl.piotrsukiennik.tuner.DataSourceManager;
import pl.piotrsukiennik.tuner.DataSourceService;
import pl.piotrsukiennik.tuner.model.datasource.DataSourceIdentity;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Piotr Sukiennik
 * @date 23.05.14
 */
@Service
public class DataSourceServiceImpl implements DataSourceService {

    private Map<DataSourceIdentity, DataSource> dataSources = new HashMap<>(  );

    private Map<String, DataSource> rootDataSources = new HashMap<String, DataSource>();

    @Override
    public Collection<DataSource> getDataSources(){
        return dataSources.values();
    }
    @Override
    public Collection<DataSourceIdentity> getDataSourceIdentities(){
        return dataSources.keySet();
    }

    @Override
    public DataSource getDataSource(DataSourceIdentity dataSourceIdentity){
        return dataSources.get( dataSourceIdentity );
    }

    @Override
    public void add( DataSource dataSource ) {
        if (dataSource!=this){
            dataSources.put( dataSource.getDataSourceIdentity(), dataSource );
        }
    }

    @Override
    public void remove( DataSourceIdentity dataSourceIdentity ) {
        dataSources.remove( dataSourceIdentity );
    }

    @Override
    public void remove( DataSource dataSource ) {
        remove( dataSource.getDataSourceIdentity() );
    }


    @Override
    public void setDefaultDataSource( ReadQuery query, DataSource dataSource ) {
        rootDataSources.put( query.getHash(), dataSource );
    }

    @Override
    public DataSource getDefaultDataSource( ReadQuery query ) {
        return rootDataSources.get( query.getHash() );
    }

    @Autowired
    public void setDataSources( Collection<DataSource> dataSources ) {
        for (DataSource dataSource: dataSources ){
            if (!(dataSource instanceof DataSourceManager )){
                add( dataSource );
            }
        }
    }
}
