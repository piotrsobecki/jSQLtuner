package pl.piotrsukiennik.tuner.service.datasource;

import pl.piotrsukiennik.tuner.exception.DataRetrievalException;

/**
 * @author Piotr Sukiennik
 * @date 22.01.14
 */
public class WrongStatementException extends DataRetrievalException {
    WrongStatementException() {
    }

    WrongStatementException( String message ) {
        super( message );
    }

    WrongStatementException( String message, Throwable cause ) {
        super( message, cause );
    }

    WrongStatementException( Throwable cause ) {
        super( cause );
    }

    WrongStatementException( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace ) {
        super( message, cause, enableSuppression, writableStackTrace );
    }
}
