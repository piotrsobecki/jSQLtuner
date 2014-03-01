package pl.piotrsukiennik.parser.impl.element;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.*;
import pl.piotrsukiennik.parser.NewQueryUtils;
import pl.piotrsukiennik.parser.QueryParsingContext;
import pl.piotrsukiennik.parser.impl.statement.ParsingVisitor;
import pl.piotrsukiennik.tuner.model.expression.OperatorExpression;
import pl.piotrsukiennik.tuner.model.other.JoinFragment;
import pl.piotrsukiennik.tuner.model.other.OrderByFragment;
import pl.piotrsukiennik.tuner.model.query.impl.SelectQuery;

import java.util.List;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 22:54
 */
public class SelectBodyParser<T extends SelectQuery> extends ParsingVisitor<T> implements net.sf.jsqlparser.statement.select.SelectVisitor {

    public SelectBodyParser( QueryParsingContext parsingContext, T query ) {
        super( parsingContext, query );
    }

    @Override
    public void visit( PlainSelect plainSelect ) {
        FromItemParser fromItemParser = new FromItemParser( parsingContext );
        plainSelect.getFromItem().accept( fromItemParser );
        NewQueryUtils.addSource( query, fromItemParser.getSource() );
        if ( plainSelect.getFromItem() != null ) {
            plainSelect.getFromItem().accept( fromItemParser );
        }

        List<SelectItem> selectItems = plainSelect.getSelectItems();
        SelectItemParser selectItemParser = new SelectItemParser( parsingContext, query );
        for ( SelectItem selectItem : selectItems ) {
            selectItem.accept( selectItemParser );
        }

        ExpresionParser expresionParser = new ExpresionParser( parsingContext );

        if ( plainSelect.getHaving() != null ) {
            plainSelect.getHaving().accept( expresionParser );
            query.setHavingExpression( (OperatorExpression) expresionParser.getExpression() );
        }

        if ( plainSelect.getWhere() != null ) {
            plainSelect.getWhere().accept( expresionParser );
            query.setWhereExpression( (OperatorExpression) expresionParser.getExpression() );
        }

        if ( plainSelect.getJoins() != null ) {
            List<Join> joins = plainSelect.getJoins();
            for ( Join join : joins ) {
                JoinFragment joinFragment = parsingContext.getJoin( join );
                NewQueryUtils.addJoin( query, joinFragment );
            }
        }

        if ( plainSelect.getOrderByElements() != null ) {
            List<OrderByElement> orderByElements = plainSelect.getOrderByElements();
            for ( int i = 0; i < orderByElements.size(); i++ ) {
                OrderByElement orderByElement = orderByElements.get( i );
                OrderByFragment orderByFragment = parsingContext.getOrderByFragment( orderByElement );
                NewQueryUtils.addOrderByFragment( query, orderByFragment );
            }

        }

        if ( plainSelect.getGroupByColumnReferences() != null ) {
            List<Expression> groupByColumns = plainSelect.getGroupByColumnReferences();
            for ( Expression column : groupByColumns ) {
                pl.piotrsukiennik.tuner.model.expression.Expression fragment = null;
                if ( column instanceof Column ) {
                    fragment = parsingContext.getColumnProjection( (Column) column );
                }
                else {
                    ExpresionParser groupByColumnExpresionParser = new ExpresionParser( parsingContext );
                    column.accept( expresionParser );
                    fragment = groupByColumnExpresionParser.getExpression();
                }
                NewQueryUtils.addGroupByFragment( query, fragment );
            }
        }

        if ( plainSelect.getInto() != null ) {
            query.setIntoTable( parsingContext.getTable( plainSelect.getInto() ) );
        }

        if ( plainSelect.getLimit() != null ) {
            Limit limit = plainSelect.getLimit();
            query.setLimitFrom( limit.getOffset() );
            query.setLimitTo( limit.getOffset() + limit.getRowCount() );
        }

        init( plainSelect );

    }

    @Override
    public void visit( SetOperationList setOpList ) {
        //TODO
    }

    @Override
    public void visit( WithItem withItem ) {
        //TODO
    }


}
