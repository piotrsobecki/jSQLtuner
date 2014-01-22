package pl.piotrsukiennik.tuner.dto;

import pl.piotrsukiennik.tuner.model.schema.Column;
import pl.piotrsukiennik.tuner.model.schema.Database;
import pl.piotrsukiennik.tuner.model.schema.Schema;
import pl.piotrsukiennik.tuner.model.schema.Table;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Piotr Sukiennik
 * Date: 28.07.13
 * Time: 19:24
 */
public class QueryContextDto {

    private Database database;

    private Schema schema;

    private Map<String, Table> tables = new HashMap<String, Table>();

    private Map<String, Column> columns = new HashMap<String, Column>();

    public Database getDatabase() {
        return database;
    }

    public void setDatabase( Database database ) {
        this.database = database;
    }

    public Schema getSchema() {
        return schema;
    }

    public void setSchema( Schema schema ) {
        this.schema = schema;
    }

    public Map<String, Table> getTables() {
        return tables;
    }

    public void setTables( Map<String, Table> tables ) {
        this.tables = tables;
    }

    public Map<String, Column> getColumns() {
        return columns;
    }

    public void setColumns( Map<String, Column> columns ) {
        this.columns = columns;
    }
}
