package pl.piotrsukiennik.tuner.parser.jsqlqueryparser.statement;

import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;
import pl.piotrsukiennik.tuner.persistance.model.query.DeleteQuery;
import pl.piotrsukiennik.tuner.query.QueryContextManager;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 23:07
 */
public class DeleteStatementParser extends StatementParser<DeleteQuery> {
    public DeleteStatementParser(QueryContextManager queryContextManager,Delete delete) {
        super(queryContextManager,delete);
    }

    @Override
    public void visit(Delete delete) {
        super.visit(delete);
    }


    protected void init(Statement o) {
        setQuery(new DeleteQuery());
        super.init(o);
    }
}
