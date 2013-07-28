package pl.piotrsukiennik.tuner.parser.jsqlqueryparser.element;

import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.ItemsListVisitor;
import net.sf.jsqlparser.statement.select.SubSelect;

/**
 * Author: Piotr Sukiennik
 * Date: 27.07.13
 * Time: 13:30
 */
public class ItemsListParser implements ItemsListVisitor {
    @Override
    public void visit(SubSelect subSelect) {

    }

    @Override
    public void visit(ExpressionList expressionList) {

    }
}
