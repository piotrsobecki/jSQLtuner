package pl.piotrsukiennik.tuner.service;

/**
 * @author Piotr Sukiennik
 * @date 21.02.14
 */
public interface QueryParsingContextBuilder<C extends QueryParsingContext> {
    C getQueryContext( String database, String schema );
}
