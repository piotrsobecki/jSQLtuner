package pl.piotrsukiennik.tuner.service.parser.element;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.ItemsListVisitor;
import net.sf.jsqlparser.expression.operators.relational.MultiExpressionList;
import net.sf.jsqlparser.statement.select.SubSelect;
import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.model.query.impl.SelectQuery;
import pl.piotrsukiennik.tuner.service.QueryContext;
import pl.piotrsukiennik.tuner.service.parser.ElementParserService;

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

    private ElementParserService elementParserService;

    public ItemsListParser( ElementParserService elementParserService, QueryContext queryContext, Query sourceQuery ) {
        this.sourceQuery = sourceQuery;
        this.queryContext = queryContext;
        this.elementParserService = elementParserService;
    }

    @Override
    public void visit( MultiExpressionList multiExprList ) {
        //TODO
    }

    @Override
    public void visit( SubSelect subSelect ) {
        SelectQuery selectQuery = new SelectQuery();
        selectQuery.setParentQuery( sourceQuery );
        FromItemParser fromItemParser = new FromItemParser( elementParserService, queryContext );
        ExpresionParser expresionParser = new ExpresionParser( elementParserService, queryContext );
        subSelect.accept( fromItemParser );
        subSelect.accept( expresionParser );
        //TODO

    }

    @Override
    public void visit( ExpressionList expressionList ) {
        Set<pl.piotrsukiennik.tuner.model.expression.Expression> expressionsOut = new LinkedHashSet<>();
        List<Expression> expressions = expressionList.getExpressions();
        for ( Expression ex : expressions ) {
            ExpresionParser expresionParser = new ExpresionParser( elementParserService, queryContext );
            ex.accept( expresionParser );
            expressionsOut.add( expresionParser.getExpression() );
        }
        //TODO
    }
}
