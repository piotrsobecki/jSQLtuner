package pl.piotrsukiennik.tuner.dto;

import pl.piotrsukiennik.tuner.model.datasource.DataSourceIdentity;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;

import javax.sql.rowset.CachedRowSet;

public class ReadQueryExecutionResultBuilder {
    private ReadQuery readQuery;

    private DataSourceIdentity dataSource;

    private CachedRowSet resultSet;

    private ReadQueryExecutionComplexityEstimation readQueryExecutionComplexityEstimation;

    public ReadQueryExecutionResultBuilder() {
    }

    public ReadQueryExecutionResultBuilder( ReadQueryExecutionResult readQueryExecutionResult) {
        this.readQuery=readQueryExecutionResult.getReadQuery();
        this.dataSource=readQueryExecutionResult.getDataSource();
        this.resultSet=readQueryExecutionResult.getResultSet();
        this.readQueryExecutionComplexityEstimation =readQueryExecutionResult.getReadQueryExecutionComplexityEstimation();
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


    public ReadQueryExecutionResultBuilder withQueryComplexityEstimation( ReadQueryExecutionComplexityEstimation readQueryExecutionComplexityEstimation ) {
        this.readQueryExecutionComplexityEstimation = readQueryExecutionComplexityEstimation;
        return this;
    }

    public ReadQueryExecutionResult build() {
        return new ReadQueryExecutionResult( readQuery, dataSource, resultSet, readQueryExecutionComplexityEstimation );
    }
}
