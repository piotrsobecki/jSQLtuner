package pl.piotrsukiennik.tuner.parser.jsqlqueryparser.statement;

import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import pl.piotrsukiennik.tuner.persistance.model.query.SelectQuery;
import pl.piotrsukiennik.tuner.parser.jsqlqueryparser.element.SelectBodyParser;
import pl.piotrsukiennik.tuner.query.QueryContextManager;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 23:15
 */
public class SelectStatementParser extends StatementParser<SelectQuery>  {
    public SelectStatementParser(QueryContextManager queryContextManager, Select select) {
        super(queryContextManager,select);

    }

    @Override
    public void visit(Select select) {
        SelectBody selectBody = select.getSelectBody();
        SelectBodyParser<SelectQuery> parsingVisitor = new SelectBodyParser<SelectQuery>(getQueryContextManager(),query);
        selectBody.accept(parsingVisitor);
        super.visit(select);
    }

    @Override
    protected void init(Object o) {
        setQuery(new SelectQuery());
        super.init(o);
    }
}
