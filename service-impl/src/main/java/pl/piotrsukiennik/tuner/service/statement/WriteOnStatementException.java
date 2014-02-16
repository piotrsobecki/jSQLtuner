package pl.piotrsukiennik.tuner.service.statement;

/**
 * @author Piotr Sukiennik
 * @date 22.01.14
 */
public class WriteOnStatementException extends RuntimeException {
    public WriteOnStatementException() {
    }

    public WriteOnStatementException( String message ) {
        super( message );
    }

    public WriteOnStatementException( String message, Throwable cause ) {
        super( message, cause );
    }

    public WriteOnStatementException( Throwable cause ) {
        super( cause );
    }

    public WriteOnStatementException( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace ) {
        super( message, cause, enableSuppression, writableStackTrace );
    }
}
