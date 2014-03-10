package pl.piotrsukiennik.tuner.service;

import pl.piotrsukiennik.tuner.model.schema.*;

/**
 * @author Piotr Sukiennik
 * @date 28.02.14
 */
public interface SchemaService {
    Database getDatabase( String database );

    Column getColumn( Table tableName, String columnName );

    Function getFunction( String database, String functionName );

    Schema getSchema( String database, String schema );

    Column getColumn( Database database, String schema, String tableName, String columnName );

    Column getColumn( String database, String schema, String tableName, String columnName );

    Function getFunction( Database database, String functionName );

    Table getTable( Database database, String schema, String tableName );

    Type getType( Database database, String typeName );

    Table getTable( Schema schema, String tableName );

    Schema getSchema( Database database, String schema );

    Index getIndex( Table table, String indexName );

    Column getColumn( Schema schema, String tableName, String columnName );

    Type getType( String database, String typeName );

    Table getTable( String database, String schema, String tableName );

}
