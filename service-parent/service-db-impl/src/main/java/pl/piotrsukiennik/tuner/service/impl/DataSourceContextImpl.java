package pl.piotrsukiennik.tuner.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.piotrsukiennik.tuner.service.DataSourceContext;


/**
 * @author Piotr Sukiennik
 * @date 16.02.14
 */
@Service
public class DataSourceContextImpl implements DataSourceContext {

    private String database;

    private String schema;

    @Autowired
    public DataSourceContextImpl( @Value("${jsqltuner.db.url}") String database, @Value("${jsqltuner.db.schema}") String schema ) {
        this.database = database;
        this.schema = schema;
    }


    @Override
    public boolean contains( String database, String schema ) {
        return this.database.equalsIgnoreCase( database ) && this.schema.equalsIgnoreCase( schema );
    }

}
