package pl.piotrsukiennik.tuner.parser.jsqlqueryparser.statement;

import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;
import pl.piotrsukiennik.tuner.parser.jsqlqueryparser.element.DeleteIntoTableParser;
import pl.piotrsukiennik.tuner.parser.jsqlqueryparser.element.ExpresionParser;
import pl.piotrsukiennik.tuner.parser.jsqlqueryparser.element.FromItemParser;
import pl.piotrsukiennik.tuner.parser.jsqlqueryparser.element.InsertIntoTableParser;
import pl.piotrsukiennik.tuner.persistance.model.query.DeleteQuery;
import pl.piotrsukiennik.tuner.persistance.model.query.source.TableSource;
import pl.piotrsukiennik.tuner.persistance.model.schema.Table;
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
        TableSource tableSource = parserUtils.getTableSource(delete.getTable());
        query.setTableSource(tableSource);
        if (delete.getWhere()!=null){
            ExpresionParser expresionParser = new ExpresionParser(queryContextManager);
            delete.getWhere().accept(expresionParser);
            query.setWhereCondition(expresionParser.getCondition());
        }
        super.visit(delete);
    }


}
