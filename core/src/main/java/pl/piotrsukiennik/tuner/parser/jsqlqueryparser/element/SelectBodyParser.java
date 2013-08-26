package pl.piotrsukiennik.tuner.parser.jsqlqueryparser.element;

import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Union;
import pl.piotrsukiennik.tuner.persistance.model.query.SelectQuery;
import pl.piotrsukiennik.tuner.parser.jsqlqueryparser.statement.ParsingVisitor;
import pl.piotrsukiennik.tuner.query.QueryContextManager;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 22:54
 */
public class SelectBodyParser<T extends SelectQuery> extends ParsingVisitor implements net.sf.jsqlparser.statement.select.SelectVisitor {
    public SelectBodyParser(QueryContextManager queryContextManager, T query) {
        super(queryContextManager,query);
    }

    @Override
    public void visit(PlainSelect plainSelect) {
        FromItemParser fromItemParser = new FromItemParser(queryContextManager,query);
        ExpresionParser expresionParser = new ExpresionParser(queryContextManager,query);
        if (plainSelect.getFromItem()!=null){
            plainSelect.getFromItem().accept(fromItemParser);
        }

        if (plainSelect.getHaving()!=null){
            plainSelect.getHaving().accept(expresionParser);
        }

        if (plainSelect.getWhere()!=null){
            plainSelect.getWhere().accept(expresionParser);
        }

        if (query instanceof SelectQuery){
            SelectIntoTableParser selectIntoTableParser = new SelectIntoTableParser(queryContextManager,(SelectQuery)query);
            if(plainSelect.getInto()!=null){
                plainSelect.getInto().accept(fromItemParser);
            }
        }
        init(plainSelect);

    }

    @Override
    public void visit(Union union) {
        init(union);
    }
}
