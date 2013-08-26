package pl.piotrsukiennik.tuner.parser.jsqlqueryparser.statement;

import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.StatementVisitor;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.update.Update;
import pl.piotrsukiennik.tuner.persistance.model.query.Query;
import pl.piotrsukiennik.tuner.persistance.model.query.UpdateQuery;
import pl.piotrsukiennik.tuner.query.QueryContextManager;
import pl.piotrsukiennik.tuner.util.HashGenerators;
import pl.piotrsukiennik.tuner.util.IHashGenerator;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 22:38
 */
public class StatementParserVisitor<T extends Query> extends StatementParser implements StatementVisitor {


    public StatementParserVisitor(QueryContextManager queryContextManager,Statement statement) {
        super(queryContextManager,statement);
    }

    protected void setHash(Query query, Statement statement){
        query.setHash(HashGenerators.MD5.getHash(statement.toString()));
    }

    @Override
    public void visit(Select select) {
        SelectStatementParser statementParser = new SelectStatementParser(getQueryContextManager(),select);
        setHash(statementParser.getQuery(),select);
        setQuery(statementParser.getQuery());
    }

    @Override
    public void visit(Delete delete) {
        DeleteStatementParser deleteStatementParser= new DeleteStatementParser(getQueryContextManager(),delete);
        setHash(deleteStatementParser.getQuery(),delete);
        setQuery(deleteStatementParser.getQuery());
    }

    @Override
    public void visit(Update update) {
        UpdateStatementParser<UpdateQuery> updateStatementParser = new UpdateStatementParser<UpdateQuery>(getQueryContextManager(),update);
        setHash(updateStatementParser.getQuery(),update);
        setQuery(updateStatementParser.getQuery());
    }

    @Override
    public void visit(Insert insert) {
        InsertStatementParser insertStatementParser = new InsertStatementParser(getQueryContextManager(),insert);
        setHash(insertStatementParser.getQuery(),insert);
        setQuery(insertStatementParser.getQuery());
    }

}
