package pl.piotrsukiennik.tuner.parser.jsqlqueryparser.element;

import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.FromItemVisitor;
import net.sf.jsqlparser.statement.select.SubJoin;
import net.sf.jsqlparser.statement.select.SubSelect;

/**
 * Author: Piotr Sukiennik
 * Date: 27.07.13
 * Time: 13:24
 */
public class FromItemParser implements FromItemVisitor {
    @Override
    public void visit(Table tableName) {

    }

    @Override
    public void visit(SubSelect subSelect) {

    }

    @Override
    public void visit(SubJoin subjoin) {

    }
}
