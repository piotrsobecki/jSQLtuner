package pl.piotrsukiennik.tuner.parser.jsqlqueryparser.statement;

import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Union;
import pl.piotrsukiennik.tuner.model.query.SelectQuery;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 22:54
 */
public class SelectBodyParser<T extends SelectQuery> extends ParsingVisitor implements net.sf.jsqlparser.statement.select.SelectVisitor {
    public SelectBodyParser(T query) {
        super(query);
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
