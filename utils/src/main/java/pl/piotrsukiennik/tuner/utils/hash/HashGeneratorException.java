package pl.piotrsukiennik.tuner.utils.hash;

/**
 * @author Piotr Sukiennik
 * @date 23.01.14
 */
public class HashGeneratorException extends RuntimeException {
    public HashGeneratorException() {
    }

    public HashGeneratorException( String message ) {
        super( message );
    }

    public HashGeneratorException( String message, Throwable cause ) {
        super( message, cause );
    }

    public HashGeneratorException( Throwable cause ) {
        super( cause );
    }

    public HashGeneratorException( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace ) {
        super( message, cause, enableSuppression, writableStackTrace );
    }
}
