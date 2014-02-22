package pl.piotrsukiennik.tuner.service.impl;

import pl.piotrsukiennik.tuner.service.parser.QueryParsingContext;

/**
 * @author Piotr Sukiennik
 * @date 21.02.14
 */
public interface QueryContextBuilder {
    QueryParsingContext getQueryContext( String database, String schema );
}
