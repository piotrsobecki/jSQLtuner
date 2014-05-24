package pl.piotrsukiennik.tuner.service;

/**
 * @author Piotr Sukiennik
 * @date 21.02.14
 */
public interface QueryElementsParsingContextBuilder<C extends QueryParsingContext> {
    C getQueryContext( QueryElementsContext queryElementsContext );
}
