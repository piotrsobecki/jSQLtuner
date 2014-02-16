package pl.piotrsukiennik.tuner.service.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import pl.piotrsukiennik.tuner.service.statement.wrapper.PSParameter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.regex.Pattern;

/**
 * Author: Piotr Sukiennik
 * Date: 28.07.13
 * Time: 14:16
 */
public class Statements {

    private static final Log LOG = LogFactory.getLog( Statements.class );

    private static final String BIND_PARAMETER_REGEX_STRING = "\\?";

    private static final Pattern BIND_PARAMETER_REGEX = Pattern.compile( BIND_PARAMETER_REGEX_STRING );

    private static final String DEFAULT_SCHEMA_NAME = "";

    private Statements() {
    }


    public static String bind( String sql, Collection<PSParameter> parameters ) {
        String sqlReplaced = sql;
        if ( parameters != null && !parameters.isEmpty() ) {
            for ( PSParameter parameter : parameters ) {
                sqlReplaced = BIND_PARAMETER_REGEX.matcher( sqlReplaced ).replaceFirst( parameter.getStringValue() );
            }
        }
        return sqlReplaced;
    }


    public static String getDatabase( Connection connection ) {
        try {
            return connection.getMetaData().getURL();
        }
        catch ( SQLException e ) {
            if ( LOG.isErrorEnabled() ) {
                LOG.error( "Could not obtain database name from connection. ", e );
            }
            return DEFAULT_SCHEMA_NAME;
        }
    }

    public static String getDatabase( PreparedStatement preparedStatement ) {
        try {
            return getDatabase( preparedStatement.getConnection() );
        }
        catch ( SQLException e ) {
            if ( LOG.isErrorEnabled() ) {
                LOG.error( "Could not obtain database name from preparedStatement. ", e );
            }
            return DEFAULT_SCHEMA_NAME;
        }
    }

    public static String getSchema( Connection connection ) {
        try {
            return connection.getCatalog();
        }
        catch ( SQLException e ) {
            if ( LOG.isErrorEnabled() ) {
                LOG.error( "Could not obtain schema name from connection. ", e );
            }
            return DEFAULT_SCHEMA_NAME;
        }
    }

    public static String getSchema( PreparedStatement preparedStatement ) {
        try {
            return getSchema( preparedStatement.getConnection() );
        }
        catch ( SQLException e ) {
            if ( LOG.isErrorEnabled() ) {
                LOG.error( "Could not obtain schema name from preparedStatement. ", e );
            }
            return DEFAULT_SCHEMA_NAME;
        }
    }
}
