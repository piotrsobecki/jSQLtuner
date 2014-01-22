package pl.piotrsukiennik.tuner.exception;

/**
 * @author Piotr Sukiennik
 * @date 22.01.14
 */
public class DataRetrievalException extends Exception {
    public DataRetrievalException() {
    }

    public DataRetrievalException( String message ) {
        super( message );
    }

    public DataRetrievalException( String message, Throwable cause ) {
        super( message, cause );
    }

    public DataRetrievalException( Throwable cause ) {
        super( cause );
    }

    public DataRetrievalException( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace ) {
        super( message, cause, enableSuppression, writableStackTrace );
    }
}
