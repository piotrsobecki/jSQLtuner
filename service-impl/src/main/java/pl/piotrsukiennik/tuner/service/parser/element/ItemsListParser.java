package pl.piotrsukiennik.tuner.service.parser.element;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.ItemsListVisitor;
import net.sf.jsqlparser.expression.operators.relational.MultiExpressionList;
import net.sf.jsqlparser.statement.select.SubSelect;
import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.model.query.impl.SelectQuery;
import pl.piotrsukiennik.tuner.service.parser.QueryParsingContext;
import pl.piotrsukiennik.tuner.service.parser.statement.Visitor;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Author: Piotr Sukiennik
 * Date: 27.07.13
 * Time: 13:30
 */
public class ItemsListParser extends Visitor implements ItemsListVisitor {

    private Query sourceQuery;


    public ItemsListParser( QueryParsingContext parsingContext, Query sourceQuery ) {
        super( parsingContext );
        this.sourceQuery = sourceQuery;

    }

    @Override
    public void visit( MultiExpressionList multiExprList ) {
        //TODO
    }

    @Override
    public void visit( SubSelect subSelect ) {
        SelectQuery selectQuery = new SelectQuery();
        selectQuery.setParentQuery( sourceQuery );
        FromItemParser fromItemParser = new FromItemParser( parsingContext );
        ExpresionParser expresionParser = new ExpresionParser( parsingContext );
        subSelect.accept( fromItemParser );
        subSelect.accept( expresionParser );
        //TODO

    }

    @Override
    public void visit( ExpressionList expressionList ) {
        Set<pl.piotrsukiennik.tuner.model.expression.Expression> expressionsOut = new LinkedHashSet<>();
        List<Expression> expressions = expressionList.getExpressions();
        for ( Expression ex : expressions ) {
            ExpresionParser expresionParser = new ExpresionParser( parsingContext );
            ex.accept( expresionParser );
            expressionsOut.add( expresionParser.getExpression() );
        }
        //TODO
    }
}
