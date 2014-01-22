package pl.piotrsukiennik.tuner.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Author: Piotr Sukiennik
 * Date: 28.07.13
 * Time: 14:16
 */
public class Statements {

    private static final Log LOG = LogFactory.getLog( Statements.class );

    private static final String DEFAULT_SCHEMA_NAME = "";

    private Statements() {
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
