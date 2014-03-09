package pl.piotrsukiennik.tuner.service.impl;

import org.springframework.stereotype.Service;
import pl.piotrsukiennik.tuner.model.schema.*;
import pl.piotrsukiennik.tuner.persistence.Dao;
import pl.piotrsukiennik.tuner.persistence.SchemaDao;
import pl.piotrsukiennik.tuner.service.SchemaService;

/**
 * @author Piotr Sukiennik
 * @date 28.02.14
 */
@Service
public class SchemaServiceImpl implements SchemaService {


    protected SchemaDao getSchemaDao() {
        return Dao.getSchema();
    }

    @Override
    public Database getDatabase( String database ) {
        return getSchemaDao().getDatabase( database );
    }

    @Override
    public Column getColumn( Table tableName, String columnName ) {
        return getSchemaDao().getColumn( tableName, columnName );
    }

    @Override
    public Function getFunction( String database, String functionName ) {
        return getSchemaDao().getFunction( database, functionName );
    }

    @Override
    public Schema getSchema( String database, String schema ) {
        return getSchemaDao().getSchema( database, schema );
    }

    @Override
    public Column getColumn( Database database, String schema, String tableName, String columnName ) {
        return getSchemaDao().getColumn( database, schema, tableName, columnName );
    }

    @Override
    public Column getColumn( String database, String schema, String tableName, String columnName ) {
        return getSchemaDao().getColumn( database, schema, tableName, columnName );
    }

    @Override
    public Function getFunction( Database database, String functionName ) {
        return getSchemaDao().getFunction( database, functionName );
    }

    @Override
    public Table getTable( Database database, String schema, String tableName ) {
        return getSchemaDao().getTable( database, schema, tableName );
    }

    @Override
    public Type getType( Database database, String typeName ) {
        return getSchemaDao().getType( database, typeName );
    }

    @Override
    public Table getTable( Schema schema, String tableName ) {
        return getSchemaDao().getTable( schema, tableName );
    }

    @Override
    public Schema getSchema( Database database, String schema ) {
        return getSchemaDao().getSchema( database, schema );
    }

    @Override
    public Index getIndex( Table table, String indexName ) {
        return getSchemaDao().getIndex( table, indexName );
    }

    @Override
    public Column getColumn( Schema schema, String tableName, String columnName ) {
        return getSchemaDao().getColumn( schema, tableName, columnName );
    }

    @Override
    public Type getType( String database, String typeName ) {
        return getSchemaDao().getType( database, typeName );
    }

    @Override
    public Table getTable( String database, String schema, String tableName ) {
        return getSchemaDao().getTable( database, schema, tableName );
    }
}
