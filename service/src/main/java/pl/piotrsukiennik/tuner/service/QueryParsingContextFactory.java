package pl.piotrsukiennik.tuner.service;

import pl.piotrsukiennik.tuner.model.QueryElementsContext;
import pl.piotrsukiennik.tuner.model.QueryParsingContext;

/**
 * @author Piotr Sukiennik
 * @date 21.02.14
 */
public interface QueryParsingContextFactory<C extends QueryParsingContext> {
    C getQueryContext( QueryElementsContext queryElementsContext );
}
