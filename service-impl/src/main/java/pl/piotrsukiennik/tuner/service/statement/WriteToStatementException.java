package pl.piotrsukiennik.tuner.service.statement;

/**
 * @author Piotr Sukiennik
 * @date 22.01.14
 */
public class WriteToStatementException extends RuntimeException {
    WriteToStatementException() {
    }

    WriteToStatementException( String message ) {
        super( message );
    }

    WriteToStatementException( String message, Throwable cause ) {
        super( message, cause );
    }

    WriteToStatementException( Throwable cause ) {
        super( cause );
    }

    WriteToStatementException( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace ) {
        super( message, cause, enableSuppression, writableStackTrace );
    }
}
