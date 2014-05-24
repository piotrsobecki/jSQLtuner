package pl.piotrsukiennik.tuner.parser;

import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.AllTableColumns;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.OrderByElement;
import pl.piotrsukiennik.tuner.model.expression.Expression;
import pl.piotrsukiennik.tuner.model.expression.projection.ColumnProjection;
import pl.piotrsukiennik.tuner.model.expression.projection.StarProjection;
import pl.piotrsukiennik.tuner.model.other.JoinFragment;
import pl.piotrsukiennik.tuner.model.other.OrderByFragment;
import pl.piotrsukiennik.tuner.model.schema.Column;
import pl.piotrsukiennik.tuner.model.schema.Index;
import pl.piotrsukiennik.tuner.model.source.Source;
import pl.piotrsukiennik.tuner.model.source.TableSource;
import pl.piotrsukiennik.tuner.service.QueryParsingContext;

/**
 * @author Piotr Sukiennik
 * @date 21.02.14
 */
public interface JsqlParserQueryParsingContext extends QueryParsingContext {
    pl.piotrsukiennik.tuner.model.schema.Table getTable( String table );

    pl.piotrsukiennik.tuner.model.schema.Table getTable( Table table );

    ColumnProjection getColumnProjection( net.sf.jsqlparser.schema.Column tableColumn );

    TableSource getTableSource( Table tableName );

    TableSource getTableSource( net.sf.jsqlparser.schema.Column column );

    JoinFragment getJoin( Join join );

    StarProjection getStarProjection( Source source );

    StarProjection getStarProjection( AllTableColumns allColumns );

    OrderByFragment getOrderByFragment( OrderByElement orderByElement );

    Column getColumn( net.sf.jsqlparser.schema.Column column );

    Column getColumn( pl.piotrsukiennik.tuner.model.schema.Table table, net.sf.jsqlparser.schema.Column column );

    Index getIndex( pl.piotrsukiennik.tuner.model.schema.Table table, net.sf.jsqlparser.statement.create.table.Index index );

    String getTableAlias( Alias alias );

    String getTableSourceValue( net.sf.jsqlparser.schema.Table table );

    Expression getExpression( Expression expression );
}
