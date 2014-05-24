package pl.piotrsukiennik.tuner.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.piotrsukiennik.tuner.model.QueryElementsContext;
import pl.piotrsukiennik.tuner.model.QueryElementsContextBuilder;
import pl.piotrsukiennik.tuner.model.QueryParsingContext;
import pl.piotrsukiennik.tuner.service.QueryElementsService;
import pl.piotrsukiennik.tuner.service.QueryParsingContextFactory;
import pl.piotrsukiennik.tuner.service.QueryParsingContextFactoryAdapter;
import pl.piotrsukiennik.tuner.service.SchemaService;

import javax.annotation.Resource;

/**
 * @author Piotr Sukiennik
 * @date 21.02.14
 */
@Service
public class QueryParsingContextFactoryAdapterImpl<C extends QueryParsingContext> implements QueryParsingContextFactoryAdapter<C> {

    @Autowired
    private SchemaService schemaService;

    @Autowired
    private QueryElementsService queryElementService;

    @Resource(name = "queryParsingContextFactoryImpl")
    private QueryParsingContextFactory<C> queryParsingContextBuilder;


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
