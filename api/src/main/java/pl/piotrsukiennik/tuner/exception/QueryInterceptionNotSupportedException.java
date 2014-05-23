package pl.piotrsukiennik.tuner.exception;

/**
 * @author Piotr Sukiennik
 * @date 16.02.14
 */
public class QueryInterceptionNotSupportedException extends RuntimeException {

    private String query;

    public QueryInterceptionNotSupportedException( String query ) {
        super();
        this.query = query;
    }

    public QueryInterceptionNotSupportedException() {
        this( "Query not intercepted" );
    }

    public String getQuery() {
        return query;
    }

}
