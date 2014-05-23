package pl.piotrsukiennik.tuner;

import pl.piotrsukiennik.tuner.dto.ReadQueryExecutionResult;
import pl.piotrsukiennik.tuner.exception.DataRetrievalException;
import pl.piotrsukiennik.tuner.model.datasource.DataSourceIdentity;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;

/**
 * Author: Piotr Sukiennik
 * Date: 31.08.13
 * Time: 18:57
 */
public interface CompositeDataSource extends DataSource {
    void setDefaultDataSource( ReadQuery query, DataSource dataSource );
    void add(DataSource dataSource);
    void remove(DataSourceIdentity dataSourceIdentity);
}
