package pl.piotrsukiennik.parser;

/**
 * @author Piotr Sukiennik
 * @date 21.02.14
 */
public interface QueryContextBuilder {
    QueryParsingContext getQueryContext( String database, String schema );
}
