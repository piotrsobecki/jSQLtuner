package pl.piotrsukiennik.tuner.model;

import pl.piotrsukiennik.tuner.model.datasource.DataSourceIdentity;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;

import javax.sql.rowset.CachedRowSet;

/**
 * @author Piotr Sukiennik
 * @date 24.05.14
 */
public interface ReadQueryExecutionResult<RQ extends ReadQuery,ECE extends ReadQueryExecutionComplexityEstimation>  extends QueryExecutionResult<RQ,ECE> {
    CachedRowSet getResultSet();
    DataSourceIdentity getDataSource();

    @Override
    RQ getQuery();

    @Override
    ECE getReadQueryExecutionComplexityEstimation();
}
