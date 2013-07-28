package pl.piotrsukiennik.tuner.persistance.service;


import pl.piotrsukiennik.tuner.persistance.model.schema.*;

/**
 * Author: Piotr Sukiennik
 * Date: 27.07.13
 * Time: 14:30
 */
public interface ISchemaService {
    Database getDatabase(String database);
    Function getFunction(Database database, String functionName);
    Function getFunction(String database, String functionName);
    Type getType(Database database, String typeName);
    Type getType(String database, String typeName);
    Schema getSchema(String database, String schema);
    Schema getSchema(Database database, String schema);
    Table getTable(String database, String schema, String tableName);
    Table getTable(Database database, String schema, String tableName);
    Table getTable(Database database, Schema schema, String tableName);
    Column getColumn(String database, String schema, String tableName, String columnName);
    Column getColumn(Database database, String schema, String tableName, String columnName);
    Column getColumn(Database database, Schema schema, String tableName, String columnName);
    Column getColumn(Database database, Schema schema, Table tableName, String columnName);
}
