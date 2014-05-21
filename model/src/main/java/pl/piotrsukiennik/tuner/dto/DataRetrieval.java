package pl.piotrsukiennik.tuner.dto;

import pl.piotrsukiennik.tuner.model.datasource.DataSourceIdentity;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;

import javax.sql.rowset.CachedRowSet;
import java.sql.ResultSet;

/**
 * Author: Piotr Sukiennik
 * Date: 01.09.13
 * Time: 13:29
 */
public class DataRetrieval {

    private DataSourceIdentity dataSource;

    private CachedRowSet resultSet;

    private ReadQuery readQuery;

    private long executionTimeNano;

    private long rows;

    private long resultSetSize;

    public DataRetrieval( ReadQuery readQuery,
                          DataSourceIdentity dataSource,
                          CachedRowSet resultSet,
                          long executionTimeNano,
                          long rows,
                          long resultSetSize) {

        this.readQuery = readQuery;
        this.dataSource = dataSource;
        this.resultSet = resultSet;
        this.executionTimeNano = executionTimeNano;
        this.rows = rows;
        this.resultSetSize=resultSetSize;
    }

    public ReadQuery getReadQuery() {
        return readQuery;
    }

    public CachedRowSet getResultSet() {
        return resultSet;
    }

    public void setResultSet( CachedRowSet resultSet ) {
        setRows( ( resultSet ).size() );
        this.resultSet = resultSet;
    }

    public long getResultSetSize() {
        return resultSetSize;
    }

    public long getExecutionTimeNano() {
        return executionTimeNano;
    }

    public void setExecutionTimeNano( long executionTimeNano ) {
        this.executionTimeNano = executionTimeNano;
    }

    public long getRows() {
        return rows;
    }

    public DataSourceIdentity getDataSource() {
        return dataSource;
    }

    public void setDataSource( DataSourceIdentity dataSource ) {
        this.dataSource = dataSource;
    }

    public void setReadQuery( ReadQuery readQuery ) {
        this.readQuery = readQuery;
    }

    public void setRows( long rows ) {
        this.rows = rows;
    }


}
