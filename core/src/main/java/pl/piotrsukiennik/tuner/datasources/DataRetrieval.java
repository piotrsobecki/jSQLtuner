package pl.piotrsukiennik.tuner.datasources;

import pl.piotrsukiennik.tuner.util.RowSet;

import javax.sql.rowset.CachedRowSet;
import java.sql.ResultSet;

/**
 * Author: Piotr Sukiennik
 * Date: 01.09.13
 * Time: 13:29
 */
public class DataRetrieval {
    private ResultSet resultSet;
    private IDataSource dataSource;
    private long executionTimeNano;
    private long rows;

    public ResultSet getResultSet() {
        return resultSet;
    }

    public void setResultSet(ResultSet resultSet) {
        if (resultSet instanceof CachedRowSet){
           rows = RowSet.size((CachedRowSet) resultSet);
        }
        this.resultSet = resultSet;
    }

    public long getExecutionTimeNano() {
        return executionTimeNano;
    }

    public void setExecutionTimeNano(long executionTimeNano) {
        this.executionTimeNano = executionTimeNano;
    }

    public long getRows() {
        return rows;
    }

    public IDataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(IDataSource dataSource) {
        this.dataSource = dataSource;
    }

}
