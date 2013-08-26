package pl.piotrsukiennik.tuner.parser.jsqlqueryparser.statement;

import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.insert.Insert;
import pl.piotrsukiennik.tuner.parser.jsqlqueryparser.element.*;
import pl.piotrsukiennik.tuner.persistance.model.query.InsertQuery;
import pl.piotrsukiennik.tuner.query.QueryContextManager;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 23:15
 */
public class InsertStatementParser extends StatementParser<InsertQuery>  {

    public InsertStatementParser(QueryContextManager queryContextManager,Insert insert) {
        super(queryContextManager,insert);
    }

    @Override
    public void visit(Insert insert) {
        FromItemParser fromItemParser = new FromItemParser(queryContextManager,query);
        InsertIntoTableParser deleteIntoTableParser = new InsertIntoTableParser(queryContextManager,query);
        ItemsListParser itemsListParser = new ItemsListParser(queryContextManager,query);
        insert.getTable().accept(deleteIntoTableParser);
        insert.getTable().accept(fromItemParser);
        insert.getItemsList().accept(itemsListParser);
        super.visit(insert);
    }


    protected void init(Statement o) {
        setQuery(new InsertQuery());
        super.init(o);
    }
}

