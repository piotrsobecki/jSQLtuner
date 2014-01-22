package pl.piotrsukiennik.tuner.exception;

/**
 * @author Piotr Sukiennik
 * @date 22.01.14
 */
public class PreparedStatementInterceptException extends Exception {
    public PreparedStatementInterceptException() {
        super();
    }

    public PreparedStatementInterceptException( String message ) {
        super( message );
    }

    public PreparedStatementInterceptException( String message, Throwable cause ) {
        super( message, cause );
    }

    public PreparedStatementInterceptException( Throwable cause ) {
        super( cause );
    }

    protected PreparedStatementInterceptException( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace ) {
        super( message, cause, enableSuppression, writableStackTrace );
    }
}
