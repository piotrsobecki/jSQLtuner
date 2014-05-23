package pl.piotrsukiennik.tuner.dto;

import pl.piotrsukiennik.tuner.model.datasource.DataSourceIdentity;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;

import javax.sql.rowset.CachedRowSet;

public class ReadQueryExecutionResultBuilder {
    private ReadQuery readQuery;

    private DataSourceIdentity dataSource;

    private CachedRowSet resultSet;

    private long executionTimeNano;

    private long rows;

    private long rowSize;

    public ReadQueryExecutionResultBuilder() {
    }

    public ReadQueryExecutionResultBuilder( ReadQueryExecutionResult readQueryExecutionResult) {
        this.readQuery=readQueryExecutionResult.getReadQuery();
        this.dataSource=readQueryExecutionResult.getDataSource();
        this.executionTimeNano=readQueryExecutionResult.getExecutionTimeNano();
        this.resultSet=readQueryExecutionResult.getResultSet();
        this.rows=readQueryExecutionResult.getRows();
        this.rowSize=readQueryExecutionResult.getRowSize();
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

    public ReadQueryExecutionResultBuilder withExecutionTimeNano( long executionTimeNano ) {
        this.executionTimeNano = executionTimeNano;
        return this;
    }

    public ReadQueryExecutionResultBuilder withRows( long rows ) {
        this.rows = rows;
        return this;
    }

    public ReadQueryExecutionResultBuilder withRowSize( long rowSize ) {
        this.rowSize = rowSize;
        return this;
    }

    public ReadQueryExecutionResult build() {
        return new ReadQueryExecutionResult( readQuery, dataSource, resultSet, executionTimeNano, rows, rowSize );
    }
}
