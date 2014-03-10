package pl.piotrsukiennik.tuner.service.impl;

import org.springframework.stereotype.Service;
import pl.piotrsukiennik.tuner.model.schema.*;
import pl.piotrsukiennik.tuner.service.SchemaService;

/**
 * @author Piotr Sukiennik
 * @date 28.02.14
 */
@Service
public class BlankSchemaServiceImpl implements SchemaService {

    @Override
    public Database getDatabase( String databaseName ) {
        Database database = new Database();
        database.setValue( databaseName );
        return database;
    }

    @Override
    public Column getColumn( Table tableName, String columnName ) {
        Column column = new Column();
        column.setValue( columnName );
        column.setTable( tableName );
        return column;
    }

    @Override
    public Function getFunction( String database, String functionName ) {

        return getFunction( getDatabase( database ), functionName );
    }

    @Override
    public Schema getSchema( String database, String schema ) {
        return getSchema( getDatabase( database ), schema );
    }

    @Override
    public Column getColumn( Database database, String schema, String tableName, String columnName ) {
        return getColumn( getSchema( database, schema ), tableName, columnName );
    }

    @Override
    public Column getColumn( String database, String schema, String tableName, String columnName ) {
        return getColumn( getDatabase( database ), schema, tableName, columnName );
    }

    @Override
    public Function getFunction( Database database, String functionName ) {
        Function function = new Function();
        function.setDatabase( database );
        function.setValue( functionName );
        return function;
    }

    @Override
    public Table getTable( Database database, String schema, String tableName ) {
        return getTable( getSchema( database, schema ), tableName );
    }

    @Override
    public Type getType( Database database, String typeName ) {
        Type type = new Type();
        type.setDatabase( database );
        type.setValue( typeName );
        return type;
    }

    @Override
    public Table getTable( Schema schema, String tableName ) {
        Table table = new Table();
        table.setSchema( schema );
        table.setValue( tableName );
        return table;
    }

    @Override
    public Schema getSchema( Database database, String schemaName ) {
        Schema schema = new Schema();
        schema.setDatabase( database );
        schema.setValue( schemaName );
        return schema;
    }

    @Override
    public Index getIndex( Table table, String indexName ) {
        Index index = new Index();
        index.setTable( table );
        index.setValue( indexName );
        return index;
    }

    @Override
    public Column getColumn( Schema schema, String tableName, String columnName ) {
        return getColumn( getTable( schema, tableName ), columnName );
    }

    @Override
    public Type getType( String database, String typeName ) {
        return getType( getDatabase( database ), typeName );
    }

    @Override
    public Table getTable( String database, String schema, String tableName ) {
        return getTable( getDatabase( database ), schema, tableName );
    }
}
