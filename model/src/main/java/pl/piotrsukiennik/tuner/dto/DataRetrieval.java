package pl.piotrsukiennik.tuner.dto;

//import pl.piotrsukiennik.tuner.IDataSource;

import javax.sql.rowset.CachedRowSet;
import java.sql.ResultSet;

/**
 * Author: Piotr Sukiennik
 * Date: 01.09.13
 * Time: 13:29
 */
public class DataRetrieval {
    private ResultSet resultSet;

    private String dataSourceIdentifier;

    private long executionTimeNano;

    private long rows;

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

    public String getDataSourceIdentifier() {
        return dataSourceIdentifier;
    }

    public void setDataSourceIdentifier( String dataSourceIdentifier ) {
        this.dataSourceIdentifier = dataSourceIdentifier;
    }

    public void setRows( long rows ) {
        this.rows = rows;
    }

}
