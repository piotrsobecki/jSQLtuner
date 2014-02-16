package pl.piotrsukiennik.tuner.exception;

/**
 * @author Piotr Sukiennik
 * @date 16.02.14
 */
public class QueryInterceptionNotSupportedException extends RuntimeException {

    private static String MESSAGE_FORMAT = "Query interception not supported - (%s).";

    public QueryInterceptionNotSupportedException() {
        super();
    }

    public QueryInterceptionNotSupportedException( String sql ) {
        super( String.format( MESSAGE_FORMAT, sql ) );
    }

    public QueryInterceptionNotSupportedException( String sql, Throwable cause ) {
        super( String.format( MESSAGE_FORMAT, sql ), cause );
    }

}
