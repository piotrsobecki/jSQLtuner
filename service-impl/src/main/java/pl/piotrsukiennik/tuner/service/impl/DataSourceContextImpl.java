package pl.piotrsukiennik.tuner.service.impl;

import pl.piotrsukiennik.tuner.service.DataSourceContext;
import pl.piotrsukiennik.tuner.service.util.Statements;

import java.sql.Connection;

/**
 * @author Piotr Sukiennik
 * @date 16.02.14
 */
public class DataSourceContextImpl implements DataSourceContext {

    private String database;

    private String schema;

    public DataSourceContextImpl( String database, String schema ) {
        this.database = database;
        this.schema = schema;
    }

    public DataSourceContextImpl( Connection connection ) {
        this.database = Statements.getDatabase( connection );
        this.schema = Statements.getSchema( connection );
    }


    @Override
    public boolean isPartOf( String database, String schema ) {
        return this.database.equalsIgnoreCase( database ) && this.schema.equalsIgnoreCase( schema );
    }

}
