package pl.piotrsukiennik.tuner.parser.jsqlqueryparser.element;

import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.ItemsListVisitor;
import net.sf.jsqlparser.statement.select.SubSelect;
import pl.piotrsukiennik.tuner.parser.jsqlqueryparser.statement.SelectStatementParser;
import pl.piotrsukiennik.tuner.persistance.model.query.InsertQuery;
import pl.piotrsukiennik.tuner.persistance.model.query.Query;
import pl.piotrsukiennik.tuner.persistance.model.query.SelectQuery;
import pl.piotrsukiennik.tuner.query.QueryContextManager;

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
        FromItemParser fromItemParser = new FromItemParser(queryContextManager,selectQuery);
        ExpresionParser expresionParser = new ExpresionParser(queryContextManager,selectQuery);
        ItemsListParser itemsListParser = new ItemsListParser(queryContextManager,selectQuery);
        subSelect.accept(fromItemParser);
        subSelect.accept(expresionParser);
        subSelect.accept(itemsListParser);
    }

    @Override
    public void visit(ExpressionList expressionList) {
        ItemsListParser itemsListParser = new ItemsListParser(queryContextManager,sourceQuery);
        expressionList.accept(itemsListParser);
    }
}
