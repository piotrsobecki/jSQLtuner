package pl.piotrsukiennik.tuner.exception;

import pl.piotrsukiennik.tuner.Loggable;
import pl.piotrsukiennik.tuner.LoggableService;

/**
 * @author Piotr Sukiennik
 * @date 16.02.14
 */
public class QueryInterceptionNotSupportedException extends RuntimeException implements Loggable {

    private String query;

    public QueryInterceptionNotSupportedException( String query ) {
        super();
        this.query = query;
    }

    public QueryInterceptionNotSupportedException() {
        this( "Query not intercepted" );
    }

    public String getQuery() {
        return query;
    }

    @Override
    public void accept( LoggableService log ) {
        log.log( this );
    }
}
