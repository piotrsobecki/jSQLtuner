package pl.piotrsukiennik.tuner.exception;

import pl.piotrsukiennik.tuner.Loggable;
import pl.piotrsukiennik.tuner.LoggableService;
import pl.piotrsukiennik.tuner.model.query.Query;

/**
 * @author Piotr Sukiennik
 * @date 16.02.14
 */
public class QueryParsingNotSupportedException extends AbstractQueryException implements Loggable {

    public QueryParsingNotSupportedException( String sql ) {
        super( sql );
    }

    public QueryParsingNotSupportedException( String message, String sql ) {
        super( message, sql );
    }

    public QueryParsingNotSupportedException( String message, Throwable cause, String sql ) {
        super( message, cause, sql );
    }

    public QueryParsingNotSupportedException( Throwable cause, String sql ) {
        super( cause, sql );
    }

    public QueryParsingNotSupportedException( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String sql ) {
        super( message, cause, enableSuppression, writableStackTrace, sql );
    }

    public QueryParsingNotSupportedException( Query query ) {
        super( query );
    }

    public QueryParsingNotSupportedException( String message, Query query ) {
        super( message, query );
    }

    public QueryParsingNotSupportedException( String message, Throwable cause, Query query ) {
        super( message, cause, query );
    }

    public QueryParsingNotSupportedException( Throwable cause, Query query ) {
        super( cause, query );
    }

    public QueryParsingNotSupportedException( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Query query ) {
        super( message, cause, enableSuppression, writableStackTrace, query );
    }

    @Override
    public void accept( LoggableService log ) {
        log.log( this );
    }
}
