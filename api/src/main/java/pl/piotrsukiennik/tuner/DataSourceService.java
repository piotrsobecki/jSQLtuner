package pl.piotrsukiennik.tuner;

import pl.piotrsukiennik.tuner.DataSource;
import pl.piotrsukiennik.tuner.model.datasource.DataSourceIdentity;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;

import java.util.Collection;

/**
 * @author Piotr Sukiennik
 * @date 23.05.14
 */
public interface DataSourceService {
    void add( DataSource dataSource );
    Collection<DataSource> getDataSources();
    Collection<DataSourceIdentity> getDataSourceIdentities();
    DataSource getDataSource(DataSourceIdentity dataSourceIdentity);
    void remove( DataSourceIdentity dataSourceIdentity );
    void remove( DataSource dataSource );
    DataSource getDefaultDataSource( ReadQuery query );
    void setDefaultDataSource( ReadQuery query, DataSource dataSource );
}
