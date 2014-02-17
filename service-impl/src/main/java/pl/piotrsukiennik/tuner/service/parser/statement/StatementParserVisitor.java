package pl.piotrsukiennik.tuner.service.parser.statement;

import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.StatementVisitor;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.drop.Drop;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.update.Update;
import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.model.query.UpdateQuery;
import pl.piotrsukiennik.tuner.service.QueryContext;
import pl.piotrsukiennik.tuner.service.parser.ElementParserService;
import pl.piotrsukiennik.tuner.service.util.hash.HashGenerators;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 22:38
 */
public class StatementParserVisitor<T extends Query> extends StatementParser implements StatementVisitor {


    public StatementParserVisitor( ElementParserService elementParserService, QueryContext queryContext, Statement statement ) {
        super( elementParserService, queryContext, statement, null );

    }

    protected void setHash( Query query, Statement statement ) {
        query.setHash( HashGenerators.MD5.getHash( statement.toString() ) );
    }

    @Override
    public void visit( Drop drop ) {
        //TODO
        //SelectStatementParser statementParser = new DropStatementParser( elementParserService, getQueryContext(), drop );
        //setHash( statementParser.getQuery(), drop );
        //setQuery( statementParser.getQuery() );
    }

    @Override
    public void visit( Select select ) {
        SelectStatementParser statementParser = new SelectStatementParser( elementParserService, getQueryContext(), select );
        setHash( statementParser.getQuery(), select );
        setQuery( statementParser.getQuery() );
    }

    @Override
    public void visit( Delete delete ) {
        DeleteStatementParser deleteStatementParser = new DeleteStatementParser( elementParserService, getQueryContext(), delete );
        setHash( deleteStatementParser.getQuery(), delete );
        setQuery( deleteStatementParser.getQuery() );
    }

    @Override
    public void visit( Update update ) {
        UpdateStatementParser<UpdateQuery> updateStatementParser = new UpdateStatementParser<UpdateQuery>( elementParserService, getQueryContext(), update );
        setHash( updateStatementParser.getQuery(), update );
        setQuery( updateStatementParser.getQuery() );
    }

    @Override
    public void visit( Insert insert ) {
        InsertStatementParser insertStatementParser = new InsertStatementParser( elementParserService, getQueryContext(), insert );
        setHash( insertStatementParser.getQuery(), insert );
        setQuery( insertStatementParser.getQuery() );
    }

}
