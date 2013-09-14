package pl.piotrsukiennik.tuner.parser.jsqlqueryparser.statement;

import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;
import pl.piotrsukiennik.tuner.parser.jsqlqueryparser.element.DeleteIntoTableParser;
import pl.piotrsukiennik.tuner.parser.jsqlqueryparser.element.ExpresionParser;
import pl.piotrsukiennik.tuner.parser.jsqlqueryparser.element.FromItemParser;
import pl.piotrsukiennik.tuner.parser.jsqlqueryparser.element.InsertIntoTableParser;
import pl.piotrsukiennik.tuner.persistance.model.query.DeleteQuery;
import pl.piotrsukiennik.tuner.query.QueryContextManager;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 23:07
 */
public class DeleteStatementParser extends StatementParser<DeleteQuery> {
    public DeleteStatementParser(QueryContextManager queryContextManager,Delete delete) {
        super(queryContextManager,delete,new DeleteQuery());
    }

    @Override
    public void visit(Delete delete) {
        FromItemParser fromItemParser = new FromItemParser(queryContextManager,query);
        DeleteIntoTableParser deleteIntoTableParser = new DeleteIntoTableParser(queryContextManager,query);
        ExpresionParser expresionParser = new ExpresionParser(queryContextManager,query);
        delete.getTable().accept(deleteIntoTableParser);
        delete.getTable().accept(fromItemParser);
        delete.getWhere().accept(expresionParser);
        super.visit(delete);
    }


}
