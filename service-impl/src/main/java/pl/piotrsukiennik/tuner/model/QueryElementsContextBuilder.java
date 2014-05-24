package pl.piotrsukiennik.tuner.model;

import pl.piotrsukiennik.tuner.service.QueryElementsService;
import pl.piotrsukiennik.tuner.service.SchemaService;
import pl.piotrsukiennik.tuner.util.GenericBuilder;

public class QueryElementsContextBuilder implements GenericBuilder<QueryElementsContext> {
    private SchemaService schemaService;

    private QueryElementsService elementService;

    private String database;

    private String schema;

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
    public QueryElementsContext build() {
        return new QueryElementsContextImpl( schemaService, elementService, database, schema );
    }
}
