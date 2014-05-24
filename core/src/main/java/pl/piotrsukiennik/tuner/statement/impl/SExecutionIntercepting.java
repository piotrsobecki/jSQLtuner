package pl.piotrsukiennik.tuner.statement.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import pl.piotrsukiennik.tuner.exception.DataRetrievalException;
import pl.piotrsukiennik.tuner.exception.QueryParsingNotSupportedException;
import pl.piotrsukiennik.tuner.model.DataSource;
import pl.piotrsukiennik.tuner.model.InterceptorDataSource;
import pl.piotrsukiennik.tuner.model.log.WriteQueryExecution;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.model.query.WriteQuery;
import pl.piotrsukiennik.tuner.service.DataSourceManager;
import pl.piotrsukiennik.tuner.service.LoggableServiceHolder;
import pl.piotrsukiennik.tuner.service.QueryProviderService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

/**
 * @author Piotr Sukiennik
 * @date 17.02.14
 */
public class SExecutionIntercepting<T extends Statement> extends SWrapper<T> implements Statement {

    private static final Log LOG = LogFactory.getLog( SExecutionIntercepting.class );

    protected DataSourceManager dataSourceManager;

    protected QueryProviderService queryService;

    protected String database;

    protected String schema;


    public SExecutionIntercepting( T statement,
                                   DataSourceManager dataSourceManager,
                                   QueryProviderService queryService,
                                   String database,
                                   String schema ) {
        super( statement );
        this.dataSourceManager = dataSourceManager;
        this.queryService = queryService;
        this.database = database;
        this.schema = schema;
    }


    protected void proceed( WriteQuery query, int rowsAffected ) {
        WriteQueryExecution writeQueryExecution = new WriteQueryExecution();
        writeQueryExecution.setRowsAffected( rowsAffected );
        writeQueryExecution.setTimestamp( new Timestamp( System.currentTimeMillis() ) );
        writeQueryExecution.setQuery( query );
        if ( rowsAffected > 0 ) {
            dataSourceManager.delete( query );
        }
    }

    protected void proceed( WriteQuery query, boolean rowsAffected ) {
        proceed( query, rowsAffected ? 1 : 0 );
    }

    protected ResultSet getResultSet( ReadQuery readQuery, DataSource rootDataSource ) throws SQLException {
        try {
            return dataSourceManager.execute( rootDataSource, readQuery );
        }
        catch ( DataRetrievalException e ) {
            throw new SQLException( e );
        }
    }
    
  /*
    @Override
    public ResultSet getResultSet() throws SQLException {
        try {
            ReadQuery readQuery =  queryService.provide(database, schema, sql );
            DataSource rootDs = new InterceptorDataSource( this, readQuery ) {
                @Override
                protected ResultSet proceed() throws SQLException {
                    return SExecutionIntercepting.super.executeQuery( sql );
                }
            };
            return getResultSet( readQuery, rootDs );
        }
        catch ( QueryParsingNotSupportedException e ) {
            LoggableServiceHolder.get().logParsingException( sql,e );
            return super.getResultSet();
        }
    }*/

    //READ
    @Override
    public ResultSet executeQuery( final String sql ) throws SQLException {
        try {
            ReadQuery readQuery = queryService.provide( database, schema, sql );
            DataSource rootDs = new InterceptorDataSource( this, readQuery ) {
                @Override
                protected ResultSet proceed() throws SQLException {
                    return SExecutionIntercepting.super.executeQuery( sql );
                }
            };
            return getResultSet( readQuery, rootDs );
        }
        catch ( QueryParsingNotSupportedException e ) {
             LoggableServiceHolder.get().log( e );
            return super.getResultSet();
        }
    }
    //!READ

    //WRITE
    @Override
    public int[] executeBatch() throws SQLException {
        return super.executeBatch();
    }

    @Override
    public int executeUpdate( String sql ) throws SQLException {
        int rowsAffected = super.executeUpdate( sql );
        try {
            WriteQuery query = queryService.provide( database, schema, sql );
            proceed( query, rowsAffected );
        }
        catch ( QueryParsingNotSupportedException e ) {
             LoggableServiceHolder.get().log( e );
        }
        return rowsAffected;
    }

    @Override
    public boolean execute( String sql ) throws SQLException {
        boolean rowsAffected = super.execute( sql );
        try {
            WriteQuery query = queryService.provide( database, schema, sql );
            proceed( query, rowsAffected );
        }
        catch ( QueryParsingNotSupportedException e ) {
             LoggableServiceHolder.get().log( e );
        }
        return rowsAffected;
    }

    @Override
    public int executeUpdate( String sql, int[] columnIndexes ) throws SQLException {
        int rowsAffected = super.executeUpdate( sql, columnIndexes );
        try {
            WriteQuery query = queryService.provide( database, schema, sql );
            proceed( query, rowsAffected );
        }
        catch ( QueryParsingNotSupportedException e ) {
             LoggableServiceHolder.get().log( e );
        }
        return rowsAffected;
    }

    @Override
    public int executeUpdate( String sql, int autoGeneratedKeys ) throws SQLException {
        int rowsAffected = super.executeUpdate( sql, autoGeneratedKeys );
        try {
            WriteQuery query = queryService.provide( database, schema, sql );
            proceed( query, rowsAffected );
        }
        catch ( QueryParsingNotSupportedException e ) {
             LoggableServiceHolder.get().log( e );
        }
        return rowsAffected;
    }

    @Override
    public int executeUpdate( String sql, String[] columnNames ) throws SQLException {
        int rowsAffected = super.executeUpdate( sql, columnNames );
        try {
            WriteQuery query = queryService.provide( database, schema, sql );
            proceed( query, rowsAffected );
        }
        catch ( QueryParsingNotSupportedException e ) {
             LoggableServiceHolder.get().log( e );
        }
        return rowsAffected;
    }

    @Override
    public boolean execute( String sql, int autoGeneratedKeys ) throws SQLException {
        boolean rowsAffected = super.execute( sql, autoGeneratedKeys );
        try {
            WriteQuery query = queryService.provide( database, schema, sql );
            proceed( query, rowsAffected );
        }
        catch ( QueryParsingNotSupportedException e ) {
             LoggableServiceHolder.get().log( e );
        }
        return rowsAffected;
    }

    @Override
    public boolean execute( String sql, int[] columnIndexes ) throws SQLException {
        boolean rowsAffected = super.execute( sql, columnIndexes );
        try {
            WriteQuery query = queryService.provide( database, schema, sql );
            proceed( query, rowsAffected );
        }
        catch ( QueryParsingNotSupportedException e ) {
             LoggableServiceHolder.get().log( e );
        }
        return rowsAffected;
    }

    @Override
    public boolean execute( String sql, String[] columnNames ) throws SQLException {
        boolean rowsAffected = super.execute( sql, columnNames );
        try {
            WriteQuery query = queryService.provide( database, schema, sql );
            proceed( query, rowsAffected );
        }
        catch ( QueryParsingNotSupportedException e ) {
             LoggableServiceHolder.get().log( e );
        }
        return rowsAffected;
    }
    //!WRITE
}
