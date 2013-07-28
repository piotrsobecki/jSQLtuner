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
        init(plainSelect);

    }

    @Override
    public void visit(Union union) {
        init(union);
    }
}
