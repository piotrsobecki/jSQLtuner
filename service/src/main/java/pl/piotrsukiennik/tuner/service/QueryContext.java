package pl.piotrsukiennik.tuner.service;

import pl.piotrsukiennik.tuner.model.query.source.TableSource;
import pl.piotrsukiennik.tuner.model.schema.Column;
import pl.piotrsukiennik.tuner.model.schema.Database;
import pl.piotrsukiennik.tuner.model.schema.Schema;
import pl.piotrsukiennik.tuner.model.schema.Table;

/**
 * @author Piotr Sukiennik
 * @date 16.02.14
 */
public interface QueryContext {
    Database getDatabase( String databaseName );

    Schema getSchema( String schemaName );

    Column getColumn( Table table, String columnName );

    Column getColumn( String tableName, String columnName );

    Table getTable( String tableName );

    TableSource mergeTableSource( TableSource tableSource );

    TableSource getTableSource( String table );

    TableSource getLastAttachedTableSource();
}
