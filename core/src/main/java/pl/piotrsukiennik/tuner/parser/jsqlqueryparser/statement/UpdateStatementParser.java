package pl.piotrsukiennik.tuner.parser.jsqlqueryparser.statement;

import net.sf.jsqlparser.statement.update.Update;
import pl.piotrsukiennik.tuner.model.query.UpdateQuery;

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
        setQuery(new UpdateQuery());
        super.visit(update);
    }
}
