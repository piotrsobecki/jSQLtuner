package pl.piotrsukiennik.tuner.service.parser.element;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.ItemsListVisitor;
import net.sf.jsqlparser.statement.select.SubSelect;
import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.model.query.SelectQuery;
import pl.piotrsukiennik.tuner.model.query.other.ColumnValue;
import pl.piotrsukiennik.tuner.service.QueryElementParserService;
import pl.piotrsukiennik.tuner.service.query.QueryContext;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Author: Piotr Sukiennik
 * Date: 27.07.13
 * Time: 13:30
 */
public class ItemsListParser implements ItemsListVisitor {

    private Query sourceQuery;

    private QueryContext queryContext;

    private QueryElementParserService queryElementParserService;

    public ItemsListParser( QueryElementParserService queryElementParserService, QueryContext queryContext, Query sourceQuery ) {
        this.sourceQuery = sourceQuery;
        this.queryContext = queryContext;
        this.queryElementParserService = queryElementParserService;
    }

    @Override
    public void visit( SubSelect subSelect ) {
        SelectQuery selectQuery = new SelectQuery();
        selectQuery.setParentQuery( sourceQuery );
        FromItemParser fromItemParser = new FromItemParser( queryElementParserService, queryContext );
        ExpresionParser expresionParser = new ExpresionParser( queryElementParserService, queryContext );
        subSelect.accept( fromItemParser );
        subSelect.accept( expresionParser );
        //TODO

    }

    @Override
    public void visit( ExpressionList expressionList ) {
        List<Expression> expressions = expressionList.getExpressions();
        Set<ColumnValue> columnValues = new LinkedHashSet<ColumnValue>();
        for ( Expression ex : expressions ) {
            ColumnValue columnValue = new ColumnValue();
            ExpresionParser expresionParser = new ExpresionParser( queryElementParserService, queryContext );
            ex.accept( expresionParser );
            columnValue.setCondition( expresionParser.getCondition() );
            columnValues.add( columnValue );
        }

    }
}
