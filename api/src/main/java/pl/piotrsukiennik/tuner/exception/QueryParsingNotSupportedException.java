package pl.piotrsukiennik.tuner.exception;

/**
 * @author Piotr Sukiennik
 * @date 16.02.14
 */
public class QueryParsingNotSupportedException extends Exception {

    private static String MESSAGE_FORMAT = "Query parsing not supported - (%s).";

    QueryParsingNotSupportedException() {
        super();
    }

    public QueryParsingNotSupportedException( String sql ) {
        super( String.format( MESSAGE_FORMAT, sql ) );
    }

    public QueryParsingNotSupportedException( String sql, Throwable cause ) {
        super( String.format( MESSAGE_FORMAT, sql ), cause );
    }

}
