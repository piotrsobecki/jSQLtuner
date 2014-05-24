package pl.piotrsukiennik.tuner.model;

import pl.piotrsukiennik.tuner.model.datasource.DataSourceIdentity;
import pl.piotrsukiennik.tuner.model.query.Query;

/**
 * @author Piotr Sukiennik
 * @date 24.05.14
 */
public interface QueryExecutionResult<Q extends Query, ECE extends ExecutionComplexityEstimation> {
    DataSourceIdentity getDataSource();
    Q getQuery();
    ECE getReadQueryExecutionComplexityEstimation();
}
