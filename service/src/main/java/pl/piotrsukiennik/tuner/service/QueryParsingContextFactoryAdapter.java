package pl.piotrsukiennik.tuner.service;

import pl.piotrsukiennik.tuner.model.QueryParsingContext;

/**
 * @author Piotr Sukiennik
 * @date 21.02.14
 */
public interface QueryParsingContextFactoryAdapter<C extends QueryParsingContext> {
    C getQueryContext( String database, String schema );
}
