package pl.piotrsukiennik.tuner.parser.impl;

import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.AllTableColumns;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.OrderByElement;
import org.apache.commons.lang.StringUtils;
import pl.piotrsukiennik.tuner.model.QueryElementsContext;
import pl.piotrsukiennik.tuner.model.expression.Expression;
import pl.piotrsukiennik.tuner.model.expression.projection.ColumnProjection;
import pl.piotrsukiennik.tuner.model.expression.projection.StarProjection;
import pl.piotrsukiennik.tuner.model.other.JoinFragment;
import pl.piotrsukiennik.tuner.model.other.OrderByFragment;
import pl.piotrsukiennik.tuner.model.schema.Column;
import pl.piotrsukiennik.tuner.model.schema.Database;
import pl.piotrsukiennik.tuner.model.schema.Index;
import pl.piotrsukiennik.tuner.model.schema.Schema;
import pl.piotrsukiennik.tuner.model.source.Source;
import pl.piotrsukiennik.tuner.model.source.TableSource;
import pl.piotrsukiennik.tuner.parser.JsqlParserQueryParsingContext;
import pl.piotrsukiennik.tuner.parser.TableSourceManager;
import pl.piotrsukiennik.tuner.parser.impl.element.ExpresionParser;
import pl.piotrsukiennik.tuner.parser.impl.element.FromItemParser;

/**
 * @author Piotr Sukiennik
 * @date 21.02.14
 */
public class JsqlParserQueryParsingContextImpl implements JsqlParserQueryParsingContext {

    private TableSourceManager tableSourceManager = new TableSourceManagerImpl();

    private QueryElementsContext queryElementsContext;


    public JsqlParserQueryParsingContextImpl( QueryElementsContext queryElementsContext ) {
        this.queryElementsContext = queryElementsContext;
    }

    @Override
    public Expression getExpression( Expression expression ) {
        if ( expression.getId() == 0 ) {
            return queryElementsContext.getExpression( expression );
        }
        return expression;
    }

    @Override
    public OrderByFragment getOrderByFragment( OrderByElement orderByElement ) {
        OrderByFragment orderByFragment = new OrderByFragment();
        orderByFragment.setOrderDirection( orderByElement.isAsc() ? OrderByFragment.Order.ASC : OrderByFragment.Order.DESC );
        ExpresionParser orderByExpresionParser = new ExpresionParser( this );
        orderByElement.getExpression().accept( orderByExpresionParser );
        orderByFragment.setOrderByExpression( orderByExpresionParser.getExpression() );
        orderByFragment = queryElementsContext.getOrderByFragment( orderByFragment );
        return orderByFragment;
    }

    public pl.piotrsukiennik.tuner.model.schema.Table getTable( String table ) {
        return queryElementsContext.getTable( table );
    }

    public pl.piotrsukiennik.tuner.model.schema.Table getTable( Table table ) {
        return this.getTable( table.getWholeTableName() );
    }

    protected TableSource getTableSourceForProjection( Table table ) {
        TableSource tableSource = null;
        if ( table.getName() == null ) {
            tableSource = tableSourceManager.getLastAttachedTableSource();
        }
        else {
            tableSource = getTableSource( table );
        }
        if ( tableSource == null ) {
            throw new RuntimeException( "TableSource cannot be obtained for column." );
        }
        return tableSource;
    }

    @Override
    public StarProjection getStarProjection( AllTableColumns allColumns ) {
        TableSource tableSource = getTableSourceForProjection( allColumns.getTable() );
        StarProjection columnProjection = new StarProjection();
        columnProjection.setSource( tableSource );
        columnProjection = queryElementsContext.getColumnProjection( columnProjection );
        return columnProjection;
    }

    public ColumnProjection getColumnProjection( net.sf.jsqlparser.schema.Column tableColumn ) {
        TableSource tableSource = getTableSourceForProjection( tableColumn.getTable() );
        pl.piotrsukiennik.tuner.model.schema.Column col = queryElementsContext.getColumn( tableSource.getTable(), tableColumn.getColumnName() );
        ColumnProjection columnProjection = new ColumnProjection();
        columnProjection.setColumn( col );
        columnProjection.setSource( tableSource );
        columnProjection = queryElementsContext.getColumnProjection( columnProjection );
        return columnProjection;
    }

    public TableSource getTableSource( Table tableName ) {
        TableSource tableSource = new TableSource();
        tableSource.setAlias( getTableAlias( tableName.getAlias() ) );
        tableSource.setValue( getTableSourceValue( tableName ) );
        tableSource.setTable( queryElementsContext.getTable( tableName.getWholeTableName() ) );
        tableSource = tableSourceManager.mergeTableSource( tableSource );
        if ( tableSource.getId() == 0 ) {
            tableSource = queryElementsContext.getTableSource( tableSource );
        }
        return tableSource;
    }

    public TableSource getTableSource( net.sf.jsqlparser.schema.Column column ) {
        return this.getTableSource( column.getTable() );
    }

    public String getTableAlias( Alias alias ) {
        return alias == null ? "" : alias.getName();
    }

    public String getTableSourceValue( net.sf.jsqlparser.schema.Table table ) {
        String alias = getTableAlias( table.getAlias() );
        if ( StringUtils.isEmpty( alias ) ) {
            return table.getWholeTableName();
        }
        else {
            return table.getWholeTableName() + " " + getTableAlias( table.getAlias() );
        }
    }

    public JoinFragment getJoin( Join join ) {
        JoinFragment joinFragment = new JoinFragment();
        joinFragment.setValue( join.toString() );

        FromItemParser joinFromItemParser = new FromItemParser( this );
        join.getRightItem().accept( joinFromItemParser );
        joinFragment.setSource( joinFromItemParser.getSource() );

        ExpresionParser parser = new ExpresionParser( this );
        join.getOnExpression().accept( parser );
        joinFragment.setOn( parser.getExpression() );
        joinFragment = queryElementsContext.getJoinFragment( joinFragment );
        return joinFragment;
    }

    @Override
    public StarProjection getStarProjection( Source source ) {
        StarProjection columnProjection = new StarProjection();
        columnProjection.setSource( source );
        columnProjection = queryElementsContext.getColumnProjection( columnProjection );
        return columnProjection;
    }

    public Column getColumn( net.sf.jsqlparser.schema.Column column ) {
        Column col = this.queryElementsContext.getColumn( column.getTable().getWholeTableName(), column.getColumnName() );
        pl.piotrsukiennik.tuner.model.schema.Table table = col.getTable();
        Schema schema = table.getSchema();
        Database database = schema.getDatabase();
        col.setValue( database.getValue() + "." + schema.getValue() + "." + table.getValue() + "." + col.getValue() );
        return col;
    }


    @Override
    public Column getColumn( pl.piotrsukiennik.tuner.model.schema.Table table, net.sf.jsqlparser.schema.Column column ) {
        return this.queryElementsContext.getColumn( table, column.getColumnName() );
    }

    public Index getIndex( pl.piotrsukiennik.tuner.model.schema.Table table, net.sf.jsqlparser.statement.create.table.Index index ) {
        return queryElementsContext.getIndex( table, index.getName() );
    }

}
