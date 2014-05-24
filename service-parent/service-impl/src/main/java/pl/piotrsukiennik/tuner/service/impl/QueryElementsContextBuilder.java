package pl.piotrsukiennik.tuner.service.impl;

import pl.piotrsukiennik.tuner.dto.QueryElementsCache;
import pl.piotrsukiennik.tuner.service.QueryElementsContext;
import pl.piotrsukiennik.tuner.service.QueryElementsService;
import pl.piotrsukiennik.tuner.service.SchemaService;
import pl.piotrsukiennik.tuner.util.GenericBuilder;

public class QueryElementsContextBuilder implements GenericBuilder<QueryElementsContext> {
    private SchemaService schemaService;

    private QueryElementsService elementService;

    private String database;

    private String schema;

    private QueryElementsCache queryElementsCache;

    public QueryElementsContextBuilder withSchemaService( SchemaService schemaService ) {
        this.schemaService = schemaService;
        return this;
    }

    public QueryElementsContextBuilder withElementService( QueryElementsService elementService ) {
        this.elementService = elementService;
        return this;
    }

    public QueryElementsContextBuilder withDatabase( String database ) {
        this.database = database;
        return this;
    }

    public QueryElementsContextBuilder withSchema( String schema ) {
        this.schema = schema;
        return this;
    }

    public QueryElementsContextBuilder withQueryElementsCache( QueryElementsCache queryElementsCache ) {
        this.queryElementsCache = queryElementsCache;
        return this;
    }

    public QueryElementsContext build() {
        return new QueryElementsContextImpl( schemaService, elementService, database, schema );
    }
}
