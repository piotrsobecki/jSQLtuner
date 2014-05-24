package pl.piotrsukiennik.tuner.parser.impl;

import org.springframework.stereotype.Service;
import pl.piotrsukiennik.tuner.model.QueryElementsContext;
import pl.piotrsukiennik.tuner.parser.JsqlParserQueryParsingContext;
import pl.piotrsukiennik.tuner.service.QueryParsingContextFactory;

/**
 * @author Piotr Sukiennik
 * @date 08.03.14
 */
@Service("queryParsingContextFactoryImpl")
public class QueryParsingContextFactoryImpl implements QueryParsingContextFactory<JsqlParserQueryParsingContext> {

    @Override
    public JsqlParserQueryParsingContext getQueryContext( QueryElementsContext queryElementsContext ) {
        return new JsqlParserQueryParsingContextImpl( queryElementsContext );
    }

}
