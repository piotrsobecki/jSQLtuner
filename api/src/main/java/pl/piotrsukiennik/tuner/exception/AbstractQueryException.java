package pl.piotrsukiennik.tuner.exception;

import pl.piotrsukiennik.tuner.model.query.Query;

/**
 * @author Piotr Sukiennik
 * @date 19.02.14
 */
public abstract class AbstractQueryException extends Exception {

    private String query;

    public AbstractQueryException( String query ) {
        this.query = query;
    }

    protected AbstractQueryException( String message, String query ) {
        super( message );
        this.query = query;
    }

    protected AbstractQueryException( String message, Throwable cause, String query ) {
        super( message, cause );
        this.query = query;
    }

    protected AbstractQueryException( Throwable cause, String query ) {
        super( cause );
        this.query = query;
    }

    protected AbstractQueryException( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String query ) {
        super( message, cause, enableSuppression, writableStackTrace );
        this.query = query;
    }

    protected AbstractQueryException( Query query ) {
        this.query = query.toString();
    }

    protected AbstractQueryException( String message, Query query ) {
        super( message );
        this.query = query.toString();
    }

    protected AbstractQueryException( String message, Throwable cause, Query query ) {
        super( message, cause );
        this.query = query.toString();
    }

    protected AbstractQueryException( Throwable cause, Query query ) {
        super( cause );
        this.query = query.toString();
    }

    protected AbstractQueryException( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Query query ) {
        super( message, cause, enableSuppression, writableStackTrace );
        this.query = query.toString();
    }

    public String getQuery() {
        return query;
    }


}
