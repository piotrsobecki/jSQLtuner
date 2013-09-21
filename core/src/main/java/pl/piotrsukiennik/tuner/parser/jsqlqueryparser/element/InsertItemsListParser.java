package pl.piotrsukiennik.tuner.parser.jsqlqueryparser.element;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.ItemsListVisitor;
import net.sf.jsqlparser.statement.select.SubSelect;
import pl.piotrsukiennik.tuner.persistance.model.query.*;
import pl.piotrsukiennik.tuner.persistance.model.query.condition.Condition;
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
public class InsertItemsListParser implements ItemsListVisitor {

    private InsertQuery insertQuery;
    private QueryContextManager queryContextManager;

    public InsertItemsListParser(QueryContextManager queryContextManager) {
        this.queryContextManager=queryContextManager;
    }

    public InsertQuery getInsertQuery() {
        return insertQuery;
    }

    @Override
    public void visit(SubSelect subSelect) {
        InsertAsSelectQuery insertAsSelectQuery = new InsertAsSelectQuery();
        SelectQuery selectQuery = new SelectQuery();
        selectQuery.setParentQuery(insertAsSelectQuery);
        FromItemParser fromItemParser = new FromItemParser(queryContextManager);
        ExpresionParser expresionParser = new ExpresionParser(queryContextManager);
        subSelect.accept(fromItemParser);
        subSelect.accept(expresionParser);
        selectQuery.setWhereCondition(expresionParser.getCondition());


        insertAsSelectQuery.setSelectQuery(selectQuery);
        insertQuery = insertAsSelectQuery;
    }

    @Override
    public void visit(ExpressionList expressionList) {
        List<Expression> expressions  = expressionList.getExpressions();
        Set<Condition> columnValues = new LinkedHashSet<Condition>();
        for (Expression ex: expressions){
            ExpresionParser expresionParser = new ExpresionParser(queryContextManager);
            ex.accept(expresionParser);
            columnValues.add(expresionParser.getCondition());
        }

        InsertWithValuesQuery insertWithValuesQuery = new InsertWithValuesQuery();
        insertWithValuesQuery.setColumnValues(columnValues);
        insertQuery=insertWithValuesQuery;
    }
}
