package pl.piotrsukiennik.tuner.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.piotrsukiennik.tuner.service.*;

import javax.annotation.Resource;

/**
 * @author Piotr Sukiennik
 * @date 21.02.14
 */
@Service
public class QueryParsingContextBuilderImpl<C extends QueryParsingContext> implements QueryParsingContextBuilder<C> {

    @Autowired
    private SchemaService schemaService;

    @Autowired
    private QueryElementsService queryElementService;

    @Resource(name = "pl.piotrsukiennik.tuner.service.QueryElementsParsingContextBuilder.impl")
    private QueryElementsParsingContextBuilder<C> queryParsingContextBuilder;


    @Override
    public C getQueryContext( String database, String schema ) {
        QueryElementsContext queryElementsContext = new QueryElementsContextBuilder()
         .withSchemaService( schemaService )
         .withElementService( queryElementService )
         .withDatabase( database )
         .withSchema( schema )
        .build();
        return queryParsingContextBuilder.getQueryContext( queryElementsContext );
    }
}
