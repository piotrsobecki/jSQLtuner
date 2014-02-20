package pl.piotrsukiennik.tuner.wrapper;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

/**
 * @author Piotr Sukiennik
 * @date 20.02.14
 */
public class DataSourceWrapper implements DataSource {

    private DataSource dataSource;


    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource( DataSource dataSource ) {
        this.dataSource = dataSource;
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public <T> T unwrap( Class<T> iface ) throws SQLException {
        return dataSource.unwrap( iface );
    }

    public void setLoginTimeout( int seconds ) throws SQLException {
        dataSource.setLoginTimeout( seconds );
    }

    public void setLogWriter( PrintWriter out ) throws SQLException {
        dataSource.setLogWriter( out );
    }

    public Connection getConnection( String username, String password ) throws SQLException {
        return dataSource.getConnection( username, password );
    }

    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return dataSource.getParentLogger();
    }

    public boolean isWrapperFor( Class<?> iface ) throws SQLException {
        return dataSource.isWrapperFor( iface );
    }

    public PrintWriter getLogWriter() throws SQLException {
        return dataSource.getLogWriter();
    }

    public int getLoginTimeout() throws SQLException {
        return dataSource.getLoginTimeout();
    }
}
