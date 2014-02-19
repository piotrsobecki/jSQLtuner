package pl.piotrsukiennik.tuner.dto;

//import pl.piotrsukiennik.tuner.IDataSource;

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

    private DataSourceIdentity dataSourceIdentity;

    private ResultSet resultSet;

    private ReadQuery readQuery;

    private long executionTimeNano;

    private long rows;

    public DataRetrieval( ReadQuery readQuery,
                          DataSourceIdentity dataSourceIdentity,
                          ResultSet resultSet,
                          long executionTimeNano,
                          long rows ) {

        this.readQuery = readQuery;
        this.dataSourceIdentity = dataSourceIdentity;
        this.resultSet = resultSet;
        this.executionTimeNano = executionTimeNano;
        this.rows = rows;
    }

    public ReadQuery getReadQuery() {
        return readQuery;
    }

    public ResultSet getResultSet() {
        return resultSet;
    }

    public void setResultSet( ResultSet resultSet ) {
        if ( resultSet instanceof CachedRowSet ) {
            setRows( ( (CachedRowSet) resultSet ).size() );
        }
        this.resultSet = resultSet;
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

    public DataSourceIdentity getDataSourceIdentity() {
        return dataSourceIdentity;
    }

    public void setDataSourceIdentity( DataSourceIdentity dataSourceIdentity ) {
        this.dataSourceIdentity = dataSourceIdentity;
    }

    public void setRows( long rows ) {
        this.rows = rows;
    }


}
