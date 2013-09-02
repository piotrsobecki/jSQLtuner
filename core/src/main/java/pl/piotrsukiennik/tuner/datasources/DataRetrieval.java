package pl.piotrsukiennik.tuner.datasources;

import pl.piotrsukiennik.tuner.util.RowSet;

import javax.sql.rowset.CachedRowSet;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Author: Piotr Sukiennik
 * Date: 01.09.13
 * Time: 13:29
 */
public class DataRetrieval {
    private ResultSet resultSet;
    private IDataSource dataSource;
    private long executionTimeMillis;
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

    public long getExecutionTimeMillis() {
        return executionTimeMillis;
    }

    public void setExecutionTimeMillis(long executionTimeMillis) {
        this.executionTimeMillis = executionTimeMillis;
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
