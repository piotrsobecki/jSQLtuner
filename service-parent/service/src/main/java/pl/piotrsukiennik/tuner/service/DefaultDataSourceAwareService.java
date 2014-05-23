package pl.piotrsukiennik.tuner.service;

import pl.piotrsukiennik.tuner.DataSource;
import pl.piotrsukiennik.tuner.DataSourcesAwareService;
import pl.piotrsukiennik.tuner.model.datasource.DataSourceIdentity;
import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;

import java.util.Collection;

/**
 * @author Piotr Sukiennik
 * @date 13.01.14
 */
public interface DefaultDataSourceAwareService extends DataSourcesAwareService {
    DataSource getDefaultDataSource( ReadQuery query );
    void setDefaultDataSource( ReadQuery query, DataSource dataSource );
}
