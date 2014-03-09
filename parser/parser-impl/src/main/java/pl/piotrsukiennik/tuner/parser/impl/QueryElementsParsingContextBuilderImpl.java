package pl.piotrsukiennik.tuner.parser.impl;

import org.springframework.stereotype.Service;
import pl.piotrsukiennik.tuner.parser.JsqlParserQueryParsingContext;
import pl.piotrsukiennik.tuner.service.QueryElements;
import pl.piotrsukiennik.tuner.service.QueryElementsParsingContextBuilder;

/**
 * @author Piotr Sukiennik
 * @date 08.03.14
 */
@Service("pl.piotrsukiennik.tuner.service.QueryElementsParsingContextBuilder.impl")
public class QueryElementsParsingContextBuilderImpl implements QueryElementsParsingContextBuilder<JsqlParserQueryParsingContext> {
    @Override
    public JsqlParserQueryParsingContext getQueryContext( QueryElements queryElements ) {
        return new JsqlParserQueryParsingContextImpl( queryElements );
    }
}
