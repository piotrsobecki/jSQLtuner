package pl.piotrsukiennik.tuner.exception;

/**
 * @author Piotr Sukiennik
 * @date 22.01.14
 */
public class WriteToStatementException extends RuntimeException {
    public WriteToStatementException() {
    }

    public WriteToStatementException( String message ) {
        super( message );
    }

    public WriteToStatementException( String message, Throwable cause ) {
        super( message, cause );
    }

    public WriteToStatementException( Throwable cause ) {
        super( cause );
    }

    public WriteToStatementException( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace ) {
        super( message, cause, enableSuppression, writableStackTrace );
    }
}
