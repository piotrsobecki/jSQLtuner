package pl.piotrsukiennik.tuner.model;

import pl.piotrsukiennik.tuner.model.datasource.DataSourceIdentity;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;

import javax.sql.rowset.CachedRowSet;

public class ReadQueryExecutionResultBuilder {
    private ReadQuery readQuery;

    private DataSourceIdentity dataSource;

    private CachedRowSet resultSet;

    private ReadQueryExecutionComplexityEstimation executionComplexityEstimation;

    public ReadQueryExecutionResultBuilder() {
    }

    public ReadQueryExecutionResultBuilder( ReadQueryExecutionResult readQueryExecutionResult) {
        this.readQuery=readQueryExecutionResult.getQuery();
        this.dataSource=readQueryExecutionResult.getDataSource();
        this.resultSet=readQueryExecutionResult.getResultSet();
        this.executionComplexityEstimation =readQueryExecutionResult.getReadQueryExecutionComplexityEstimation();
    }
    
    public ReadQueryExecutionResultBuilder withReadQuery( ReadQuery readQuery ) {
        this.readQuery = readQuery;
        return this;
    }

    public ReadQueryExecutionResultBuilder withDataSource( DataSourceIdentity dataSource ) {
        this.dataSource = dataSource;
        return this;
    }

    public ReadQueryExecutionResultBuilder withResultSet( CachedRowSet resultSet ) {
        this.resultSet = resultSet;
        return this;
    }


    public ReadQueryExecutionResultBuilder withQueryComplexityEstimation( ReadQueryExecutionComplexityEstimation executionComplexityEstimation ) {
        this.executionComplexityEstimation = executionComplexityEstimation;
        return this;
    }

    public ReadQueryExecutionResult build() {
        return new ReadQueryExecutionResultImpl( readQuery, dataSource, resultSet, executionComplexityEstimation );
    }
}
