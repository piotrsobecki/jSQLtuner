package pl.piotrsukiennik.tuner.datasources;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import pl.piotrsukiennik.tuner.DataSourceMetaData;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Author: Piotr Sukiennik
 * Date: 26.08.13
 * Time: 21:15
 */
public class ConnectionDataSourceMetaData implements DataSourceMetaData {

    private static final Log LOG = LogFactory.getLog( ConnectionDataSourceMetaData.class );

    public static final String DEFAULT_IDENTIFIER = "ConnectionDataSource";

    private Connection connection;

    private String identifier;

    public ConnectionDataSourceMetaData( Connection connection ) {
        this.connection = connection;
    }

    @Override
    public String getIdentifier() {
        if ( identifier != null ) {
            return identifier;
        }
        try {
            return identifier = connection.getMetaData().getURL();
        }
        catch ( SQLException s ) {
            if ( LOG.isTraceEnabled() ) {
                LOG.trace( "Exception occured while trying to retreive identifier for connection datasource", s );
            }
            return DEFAULT_IDENTIFIER;
        }
    }
}
