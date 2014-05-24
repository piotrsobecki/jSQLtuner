package pl.piotrsukiennik.tuner.model;

import pl.piotrsukiennik.tuner.model.datasource.DataSourceIdentity;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;

import javax.sql.rowset.CachedRowSet;

/**
 * Author: Piotr Sukiennik
 * Date: 01.09.13
 * Time: 13:29
 */
public class ReadQueryExecutionResultImpl implements ReadQueryExecutionResult {

    private DataSourceIdentity dataSource;

    private CachedRowSet resultSet;

    private ReadQuery readQuery;

    private ReadQueryExecutionComplexityEstimation readQueryExecutionComplexityEstimation;

    ReadQueryExecutionResultImpl( ReadQuery readQuery,
                                     DataSourceIdentity dataSource,
                                     CachedRowSet resultSet,
                                     ReadQueryExecutionComplexityEstimation readQueryExecutionComplexityEstimation ) {

        this.readQuery = readQuery;
        this.dataSource = dataSource;
        this.resultSet = resultSet;
        this.readQueryExecutionComplexityEstimation = readQueryExecutionComplexityEstimation;
    }

    @Override public ReadQuery getQuery() {
        return readQuery;
    }

    @Override public CachedRowSet getResultSet() {
        return resultSet;
    }

    @Override public ReadQueryExecutionComplexityEstimation getReadQueryExecutionComplexityEstimation() {
        return readQueryExecutionComplexityEstimation;
    }


    @Override public DataSourceIdentity getDataSource() {
        return dataSource;
    }


}
