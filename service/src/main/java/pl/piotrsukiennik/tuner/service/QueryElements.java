package pl.piotrsukiennik.tuner.service;

import pl.piotrsukiennik.tuner.model.expression.Expression;
import pl.piotrsukiennik.tuner.model.other.GroupByFragment;
import pl.piotrsukiennik.tuner.model.schema.*;

/**
 * @author Piotr Sukiennik
 * @date 16.02.14
 */
public interface QueryElements {
    Database getDatabase( String databaseName );

    Schema getSchema( String schemaName );

    GroupByFragment getGroupByFragment( Expression element, int position );

    Column getColumn( Table table, String columnName );

    Column getColumn( String tableName, String columnName );

    Index getIndex( Table table, String indexName );

    Table getTable( String tableName );

}
