package pl.piotrsukiennik.tuner.service;

import pl.piotrsukiennik.tuner.DataSource;
import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.model.query.execution.DataSourceIdentity;

import java.util.Collection;

/**
 * @author Piotr Sukiennik
 * @date 13.01.14
 */
public interface LocalDataSourceService {
    DataSource getRootDataSource( Query selectQuery );

    void setRootDataSource( Query query, DataSource dataSource );

    Collection<DataSource> getLocal( Query query, DataSourceIdentity dataSourceIdentity );
}
