package pl.piotrsukiennik.tuner.parser.jsqlqueryparser.element;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.ItemsListVisitor;
import net.sf.jsqlparser.statement.select.SubSelect;
import pl.piotrsukiennik.tuner.parser.jsqlqueryparser.statement.SelectStatementParser;
import pl.piotrsukiennik.tuner.persistance.model.query.InsertQuery;
import pl.piotrsukiennik.tuner.persistance.model.query.Query;
import pl.piotrsukiennik.tuner.persistance.model.query.SelectQuery;
import pl.piotrsukiennik.tuner.persistance.model.query.other.ColumnValue;
import pl.piotrsukiennik.tuner.query.QueryContextManager;

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
    private QueryContextManager queryContextManager;

    public ItemsListParser(QueryContextManager queryContextManager, Query sourceQuery) {
        this.sourceQuery=sourceQuery;
        this.queryContextManager=queryContextManager;
    }

    @Override
    public void visit(SubSelect subSelect) {
        SelectQuery selectQuery = new SelectQuery();
        selectQuery.setParentQuery(sourceQuery);
        FromItemParser fromItemParser = new FromItemParser(queryContextManager);
        ExpresionParser expresionParser = new ExpresionParser(queryContextManager);
        subSelect.accept(fromItemParser);
        subSelect.accept(expresionParser);
        //TODO



    }

    @Override
    public void visit(ExpressionList expressionList) {
        List<Expression> expressions  = expressionList.getExpressions();
        Set<ColumnValue> columnValues = new LinkedHashSet<ColumnValue>();
        for (Expression ex: expressions){
            ColumnValue columnValue = new ColumnValue();
            ExpresionParser expresionParser = new ExpresionParser(queryContextManager);
            ex.accept(expresionParser);
            columnValue.setCondition(expresionParser.getCondition());
            columnValues.add(columnValue);
        }

    }
}
