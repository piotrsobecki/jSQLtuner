package pl.piotrsukiennik.tuner.parser.impl.element;

import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.ItemsListVisitor;
import net.sf.jsqlparser.expression.operators.relational.MultiExpressionList;
import net.sf.jsqlparser.statement.select.SubSelect;
import pl.piotrsukiennik.tuner.model.expression.Expression;
import pl.piotrsukiennik.tuner.model.expression.OperatorExpression;
import pl.piotrsukiennik.tuner.model.query.InsertQuery;
import pl.piotrsukiennik.tuner.model.query.impl.InsertAsSelectQuery;
import pl.piotrsukiennik.tuner.model.query.impl.InsertWithValuesQuery;
import pl.piotrsukiennik.tuner.model.query.impl.SelectQuery;
import pl.piotrsukiennik.tuner.parser.JsqlParserQueryParsingContext;
import pl.piotrsukiennik.tuner.parser.impl.statement.Visitor;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Author: Piotr Sukiennik
 * Date: 27.07.13
 * Time: 13:30
 */
public class InsertItemsListParser extends Visitor implements ItemsListVisitor {

    private InsertQuery insertQuery;


    public InsertItemsListParser( JsqlParserQueryParsingContext parsingContext ) {
        super( parsingContext );
    }

    public InsertQuery getInsertQuery() {
        return insertQuery;
    }


    @Override
    public void visit( MultiExpressionList multiExprList ) {
        //TODO
    }

    @Override
    public void visit( SubSelect subSelect ) {
        InsertAsSelectQuery insertAsSelectQuery = new InsertAsSelectQuery();
        SelectQuery selectQuery = new SelectQuery();
        selectQuery.setParentQuery( insertAsSelectQuery );
        FromItemParser fromItemParser = new FromItemParser( parsingContext );
        ExpresionParser expresionParser = new ExpresionParser( parsingContext );
        subSelect.accept( fromItemParser );
        subSelect.accept( expresionParser );
        selectQuery.setWhereExpression( (OperatorExpression) expresionParser.getExpression() );

        insertAsSelectQuery.setSelectQuery( selectQuery );
        insertQuery = insertAsSelectQuery;
    }

    @Override
    public void visit( ExpressionList expressionList ) {
        List<net.sf.jsqlparser.expression.Expression> expressions = expressionList.getExpressions();
        Set<Expression> columnValues = new LinkedHashSet<Expression>();
        for ( net.sf.jsqlparser.expression.Expression ex : expressions ) {
            ExpresionParser expresionParser = new ExpresionParser( parsingContext );
            ex.accept( expresionParser );
            columnValues.add( expresionParser.getExpression() );
        }

        InsertWithValuesQuery insertWithValuesQuery = new InsertWithValuesQuery();
        insertWithValuesQuery.setColumnValues( columnValues );
        insertQuery = insertWithValuesQuery;
    }
}
