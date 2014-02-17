package pl.piotrsukiennik.tuner.exception;

/**
 * @author Piotr Sukiennik
 * @date 22.01.14
 */
public class WrongStatementException extends DataRetrievalException {
    public WrongStatementException() {
    }

    public WrongStatementException( String message ) {
        super( message );
    }

    public WrongStatementException( String message, Throwable cause ) {
        super( message, cause );
    }

    public WrongStatementException( Throwable cause ) {
        super( cause );
    }

    public WrongStatementException( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace ) {
        super( message, cause, enableSuppression, writableStackTrace );
    }
}
