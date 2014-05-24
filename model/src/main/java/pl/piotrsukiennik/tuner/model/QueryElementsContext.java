package pl.piotrsukiennik.tuner.model;

import pl.piotrsukiennik.tuner.model.expression.Expression;
import pl.piotrsukiennik.tuner.model.expression.projection.ColumnProjection;
import pl.piotrsukiennik.tuner.model.expression.projection.StarProjection;
import pl.piotrsukiennik.tuner.model.other.JoinFragment;
import pl.piotrsukiennik.tuner.model.other.OrderByFragment;
import pl.piotrsukiennik.tuner.model.schema.*;
import pl.piotrsukiennik.tuner.model.source.TableSource;

/**
 * @author Piotr Sukiennik
 * @date 16.02.14
 */
public interface QueryElementsContext {
    Database getDatabase( String databaseName );

    Schema getSchema( String schemaName );

    StarProjection getColumnProjection( StarProjection starProjection );

    OrderByFragment getOrderByFragment( OrderByFragment orderByFragment );

    ColumnProjection getColumnProjection( ColumnProjection projection );

    JoinFragment getJoinFragment( JoinFragment joinFragment );

    Column getColumn( Table table, String columnName );

    Column getColumn( String tableName, String columnName );

    Index getIndex( Table table, String indexName );

    Table getTable( String tableName );

    TableSource getTableSource( TableSource tableSource );

    <E extends Expression> E getExpression( E expression );
}
