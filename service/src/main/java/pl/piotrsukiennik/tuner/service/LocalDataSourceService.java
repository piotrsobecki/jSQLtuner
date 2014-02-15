package pl.piotrsukiennik.tuner.service;

import pl.piotrsukiennik.tuner.DataSource;
import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.model.query.datasource.DataSourceIdentity;

import java.util.Collection;

/**
 * @author Piotr Sukiennik
 * @date 13.01.14
 */
public interface LocalDataSourceService {
    DataSource getRootDataSource( Query selectQuery );

    void setRootDataSource( Query query, DataSource dataSource );

    Collection<DataSource> getLocal( Query query, DataSourceIdentity dataSourceIdentity );

    DataSource getSingleLocal( Query query, DataSourceIdentity dataSourceIdentity );
}
