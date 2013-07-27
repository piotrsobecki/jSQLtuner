package pl.piotrsukiennik.tuner.parser.jsqlqueryparser.statement;

import net.sf.jsqlparser.statement.insert.Insert;
import pl.piotrsukiennik.tuner.model.query.InsertQuery;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 23:15
 */
public class InsertStatementParser extends StatementParser<InsertQuery>  {

    public InsertStatementParser(Insert insert) {
        super(insert);
    }

    @Override
    public void visit(Insert insert) {
        super.visit(insert);
    }

    @Override
    protected void init(Object o) {
        setQuery(new InsertQuery());
        super.init(o);
    }
}

