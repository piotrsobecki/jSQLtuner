package pl.piotrsukiennik.tuner.parser.jsqlqueryparser.statement;

import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.update.Update;
import pl.piotrsukiennik.tuner.persistance.model.query.UpdateQuery;

import java.util.List;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 23:07
 */
public class UpdateStatementParser<U extends UpdateQuery> extends StatementParser<UpdateQuery>{
    public UpdateStatementParser(Update update) {
        super(update);
    }

    @Override
    public void visit(Update update) {
        List<Column> columnList = update.getColumns();
        for (Column column: columnList){
        }
        super.visit(update);
    }
}
