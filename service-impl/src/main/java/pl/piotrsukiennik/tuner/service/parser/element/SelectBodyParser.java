package pl.piotrsukiennik.tuner.service.parser.element;

import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.*;
import pl.piotrsukiennik.tuner.model.query.SelectQuery;
import pl.piotrsukiennik.tuner.model.query.other.JoinFragment;
import pl.piotrsukiennik.tuner.model.query.other.OrderByFragment;
import pl.piotrsukiennik.tuner.model.query.projection.ColumnProjection;
import pl.piotrsukiennik.tuner.service.QueryElementParserService;
import pl.piotrsukiennik.tuner.service.parser.statement.ParsingVisitor;
import pl.piotrsukiennik.tuner.service.query.QueryContext;
import pl.piotrsukiennik.tuner.service.util.QueryUtils;

import java.util.List;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 22:54
 */
public class SelectBodyParser<T extends SelectQuery> extends ParsingVisitor<T> implements net.sf.jsqlparser.statement.select.SelectVisitor {
    public SelectBodyParser( QueryElementParserService queryElementParserService, QueryContext queryContext, T query ) {
        super( queryElementParserService, queryContext, query );
    }

    @Override
    public void visit( PlainSelect plainSelect ) {
        FromItemParser fromItemParser = new FromItemParser( queryElementParserService, queryContext );
        plainSelect.getFromItem().accept( fromItemParser );
        QueryUtils.addSource( query, fromItemParser.getSource() );
        if ( plainSelect.getFromItem() != null ) {
            plainSelect.getFromItem().accept( fromItemParser );
        }

        List<SelectItem> selectItems = plainSelect.getSelectItems();
        SelectItemParser selectItemParser = new SelectItemParser( queryElementParserService, queryContext, query );
        for ( SelectItem selectItem : selectItems ) {
            selectItem.accept( selectItemParser );
        }

        ExpresionParser expresionParser = new ExpresionParser( queryElementParserService, queryContext );

        if ( plainSelect.getHaving() != null ) {
            plainSelect.getHaving().accept( expresionParser );
            query.setHavingCondition( expresionParser.getCondition() );
        }

        if ( plainSelect.getWhere() != null ) {
            plainSelect.getWhere().accept( expresionParser );
            query.setWhereCondition( expresionParser.getCondition() );
        }

        if ( plainSelect.getJoins() != null ) {
            List<Join> joins = plainSelect.getJoins();
            for ( Join join : joins ) {
                JoinFragment joinFragment = queryElementParserService.getJoin( queryContext, join );
                QueryUtils.addJoin( query, joinFragment );
            }
        }

        if ( plainSelect.getOrderByElements() != null ) {
            List<OrderByElement> orderByElements = plainSelect.getOrderByElements();
            for ( int i = 0; i < orderByElements.size(); i++ ) {
                OrderByElement orderByElement = orderByElements.get( i );
                OrderByFragment orderByFragment = queryElementParserService.getOrderByFragment( queryContext, orderByElement );
                QueryUtils.addOrderByFragment( query, orderByFragment );
            }

        }

        if ( plainSelect.getGroupByColumnReferences() != null ) {
            List<Column> groupByColumns = plainSelect.getGroupByColumnReferences();
            for ( Column column : groupByColumns ) {
                ColumnProjection columnProjection = queryElementParserService.getColumnProjection( queryContext, column );
                QueryUtils.addGroupByFragment( query, columnProjection );
            }

        }

        if ( plainSelect.getInto() != null ) {
            query.setIntoTable( queryContext.getTable( plainSelect.getInto().getWholeTableName() ) );
        }

        if ( plainSelect.getLimit() != null ) {
            Limit limit = plainSelect.getLimit();
            query.setLimitFrom( limit.getOffset() );
            query.setLimitTo( limit.getOffset() + limit.getRowCount() );
        }

        init( plainSelect );

    }

    @Override
    public void visit( Union union ) {
        init( union );
    }
}
