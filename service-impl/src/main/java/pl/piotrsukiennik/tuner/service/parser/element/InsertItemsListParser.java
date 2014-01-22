package pl.piotrsukiennik.tuner.service.parser.element;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.ItemsListVisitor;
import net.sf.jsqlparser.statement.select.SubSelect;
import pl.piotrsukiennik.tuner.model.query.InsertAsSelectQuery;
import pl.piotrsukiennik.tuner.model.query.InsertQuery;
import pl.piotrsukiennik.tuner.model.query.InsertWithValuesQuery;
import pl.piotrsukiennik.tuner.model.query.SelectQuery;
import pl.piotrsukiennik.tuner.model.query.condition.Condition;
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
public class InsertItemsListParser implements ItemsListVisitor {

    private InsertQuery insertQuery;

    private QueryContext queryContext;

    private QueryElementParserService queryElementParserService;

    public InsertItemsListParser( QueryElementParserService queryElementParserService, QueryContext queryContext ) {
        this.queryContext = queryContext;
        this.queryElementParserService = queryElementParserService;
    }

    public InsertQuery getInsertQuery() {
        return insertQuery;
    }

    @Override
    public void visit( SubSelect subSelect ) {
        InsertAsSelectQuery insertAsSelectQuery = new InsertAsSelectQuery();
        SelectQuery selectQuery = new SelectQuery();
        selectQuery.setParentQuery( insertAsSelectQuery );
        FromItemParser fromItemParser = new FromItemParser( queryElementParserService, queryContext );
        ExpresionParser expresionParser = new ExpresionParser( queryElementParserService, queryContext );
        subSelect.accept( fromItemParser );
        subSelect.accept( expresionParser );
        selectQuery.setWhereCondition( expresionParser.getCondition() );

        insertAsSelectQuery.setSelectQuery( selectQuery );
        insertQuery = insertAsSelectQuery;
    }

    @Override
    public void visit( ExpressionList expressionList ) {
        List<Expression> expressions = expressionList.getExpressions();
        Set<Condition> columnValues = new LinkedHashSet<Condition>();
        for ( Expression ex : expressions ) {
            ExpresionParser expresionParser = new ExpresionParser( queryElementParserService, queryContext );
            ex.accept( expresionParser );
            columnValues.add( expresionParser.getCondition() );
        }

        InsertWithValuesQuery insertWithValuesQuery = new InsertWithValuesQuery();
        insertWithValuesQuery.setColumnValues( columnValues );
        insertQuery = insertWithValuesQuery;
    }
}
