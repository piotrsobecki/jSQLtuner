package pl.piotrsukiennik.tuner.parser.jsqlqueryparser.statement;

import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import pl.piotrsukiennik.tuner.model.query.SelectQuery;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 23:15
 */
public class SelectStatementParser extends StatementParser<SelectQuery>  {
    public SelectStatementParser(Select select) {
        super(select);

    }

    @Override
    public void visit(Select select) {
        SelectBody selectBody = select.getSelectBody();
        SelectBodyParser<SelectQuery> parsingVisitor = new SelectBodyParser<SelectQuery>(query);
        selectBody.accept(parsingVisitor);
        super.visit(select);
    }

    @Override
    protected void init(Object o) {
        setQuery(new SelectQuery());
        super.init(o);
    }
}
