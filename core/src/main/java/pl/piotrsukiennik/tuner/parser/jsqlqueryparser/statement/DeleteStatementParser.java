package pl.piotrsukiennik.tuner.parser.jsqlqueryparser.statement;

import net.sf.jsqlparser.statement.delete.Delete;
import pl.piotrsukiennik.tuner.model.query.DeleteQuery;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 23:07
 */
public class DeleteStatementParser extends StatementParser<DeleteQuery> {
    public DeleteStatementParser(Delete delete) {
        super(delete);
    }

    @Override
    public void visit(Delete delete) {

        super.visit(delete);
    }

    @Override
    protected void init(Object o) {
        setQuery(new DeleteQuery());
        super.init(o);
    }
}
