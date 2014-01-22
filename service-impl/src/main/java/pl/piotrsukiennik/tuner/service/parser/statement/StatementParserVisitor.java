package pl.piotrsukiennik.tuner.service.parser.statement;

import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.StatementVisitor;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.update.Update;
import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.model.query.UpdateQuery;
import pl.piotrsukiennik.tuner.service.QueryElementParserService;
import pl.piotrsukiennik.tuner.service.query.QueryContext;
import pl.piotrsukiennik.tuner.utils.HashGenerators;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 22:38
 */
public class StatementParserVisitor<T extends Query> extends StatementParser implements StatementVisitor {


    public StatementParserVisitor( QueryElementParserService queryElementParserService, QueryContext queryContext, Statement statement ) {
        super( queryElementParserService, queryContext, statement, null );

    }

    protected void setHash( Query query, Statement statement ) {
        query.setHash( HashGenerators.MD5.getHash( statement.toString() ) );
    }

    @Override
    public void visit( Select select ) {
        SelectStatementParser statementParser = new SelectStatementParser( queryElementParserService, getQueryContext(), select );
        setHash( statementParser.getQuery(), select );
        setQuery( statementParser.getQuery() );
    }

    @Override
    public void visit( Delete delete ) {
        DeleteStatementParser deleteStatementParser = new DeleteStatementParser( queryElementParserService, getQueryContext(), delete );
        setHash( deleteStatementParser.getQuery(), delete );
        setQuery( deleteStatementParser.getQuery() );
    }

    @Override
    public void visit( Update update ) {
        UpdateStatementParser<UpdateQuery> updateStatementParser = new UpdateStatementParser<UpdateQuery>( queryElementParserService, getQueryContext(), update );
        setHash( updateStatementParser.getQuery(), update );
        setQuery( updateStatementParser.getQuery() );
    }

    @Override
    public void visit( Insert insert ) {
        InsertStatementParser insertStatementParser = new InsertStatementParser( queryElementParserService, getQueryContext(), insert );
        setHash( insertStatementParser.getQuery(), insert );
        setQuery( insertStatementParser.getQuery() );
    }

}
