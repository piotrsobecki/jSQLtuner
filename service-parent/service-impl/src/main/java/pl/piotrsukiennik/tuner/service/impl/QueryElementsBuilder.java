package pl.piotrsukiennik.tuner.service.impl;

import pl.piotrsukiennik.tuner.dto.QueryElementsCache;
import pl.piotrsukiennik.tuner.service.QueryElementsService;
import pl.piotrsukiennik.tuner.service.QueryElements;
import pl.piotrsukiennik.tuner.service.SchemaService;
import pl.piotrsukiennik.tuner.util.GenericBuilder;

public class QueryElementsBuilder implements GenericBuilder<QueryElements> {
    private SchemaService schemaService;

    private QueryElementsService elementService;

    private String database;

    private String schema;

    private QueryElementsCache queryElementsCache;

    public QueryElementsBuilder withSchemaService( SchemaService schemaService ) {
        this.schemaService = schemaService;
        return this;
    }

    public QueryElementsBuilder withElementService( QueryElementsService elementService ) {
        this.elementService = elementService;
        return this;
    }

    public QueryElementsBuilder withDatabase( String database ) {
        this.database = database;
        return this;
    }

    public QueryElementsBuilder withSchema( String schema ) {
        this.schema = schema;
        return this;
    }

    public QueryElementsBuilder withQueryElementsCache( QueryElementsCache queryElementsCache ) {
        this.queryElementsCache = queryElementsCache;
        return this;
    }

    public QueryElements build() {
        return new QueryElementsImpl( schemaService, elementService, database, schema );
    }
}
