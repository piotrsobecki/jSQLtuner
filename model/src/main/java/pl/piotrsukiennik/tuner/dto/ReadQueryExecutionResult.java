package pl.piotrsukiennik.tuner.dto;

import pl.piotrsukiennik.tuner.model.datasource.DataSourceIdentity;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;

import javax.sql.rowset.CachedRowSet;

/**
 * Author: Piotr Sukiennik
 * Date: 01.09.13
 * Time: 13:29
 */
public class ReadQueryExecutionResult {

    private DataSourceIdentity dataSource;

    private CachedRowSet resultSet;

    private ReadQuery readQuery;

    private ReadQueryExecutionComplexityEstimation readQueryExecutionComplexityEstimation;

    ReadQueryExecutionResult( ReadQuery readQuery,
                                     DataSourceIdentity dataSource,
                                     CachedRowSet resultSet,
                                     ReadQueryExecutionComplexityEstimation readQueryExecutionComplexityEstimation ) {

        this.readQuery = readQuery;
        this.dataSource = dataSource;
        this.resultSet = resultSet;
        this.readQueryExecutionComplexityEstimation = readQueryExecutionComplexityEstimation;
    }

    public ReadQuery getReadQuery() {
        return readQuery;
    }

    public CachedRowSet getResultSet() {
        return resultSet;
    }

    public ReadQueryExecutionComplexityEstimation getReadQueryExecutionComplexityEstimation() {
        return readQueryExecutionComplexityEstimation;
    }


    public DataSourceIdentity getDataSource() {
        return dataSource;
    }


}
