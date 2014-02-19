package pl.piotrsukiennik.tuner.service.parser.statement;

import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.StatementVisitor;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.drop.Drop;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.replace.Replace;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.truncate.Truncate;
import net.sf.jsqlparser.statement.update.Update;
import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.model.query.impl.UpdateQuery;
import pl.piotrsukiennik.tuner.service.QueryContext;
import pl.piotrsukiennik.tuner.service.parser.ElementParserService;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 22:38
 */
public class StatementParserVisitor<T extends Query> extends StatementParser implements StatementVisitor {


    public StatementParserVisitor( ElementParserService elementParserService, QueryContext queryContext, Statement statement ) {
        super( elementParserService, queryContext, statement );
    }

    @Override
    public void visit( Replace replace ) {
        ReplaceStatementParser statementParser = new ReplaceStatementParser( elementParserService, getQueryContext(), replace );
        setQuery( statementParser.getQuery() );
    }

    @Override
    public void visit( Truncate truncate ) {
        TruncateTableStatementParser statementParser = new TruncateTableStatementParser( elementParserService, getQueryContext(), truncate );
        setQuery( statementParser.getQuery() );
    }

    @Override
    public void visit( CreateTable createTable ) {
        CreateTableStatementParser statementParser = new CreateTableStatementParser( elementParserService, getQueryContext(), createTable );
        setQuery( statementParser.getQuery() );
    }

    @Override
    public void visit( Drop drop ) {
        DropStatementParser statementParser = new DropStatementParser( elementParserService, getQueryContext(), drop );
        setQuery( statementParser.getQuery() );
    }

    @Override
    public void visit( Select select ) {
        SelectStatementParser statementParser = new SelectStatementParser( elementParserService, getQueryContext(), select );
        setQuery( statementParser.getQuery() );
    }

    @Override
    public void visit( Delete delete ) {
        DeleteStatementParser deleteStatementParser = new DeleteStatementParser( elementParserService, getQueryContext(), delete );
        setQuery( deleteStatementParser.getQuery() );
    }

    @Override
    public void visit( Update update ) {
        UpdateStatementParser<UpdateQuery> updateStatementParser = new UpdateStatementParser<UpdateQuery>( elementParserService, getQueryContext(), update );
        setQuery( updateStatementParser.getQuery() );
    }

    @Override
    public void visit( Insert insert ) {
        InsertStatementParser insertStatementParser = new InsertStatementParser( elementParserService, getQueryContext(), insert );
        setQuery( insertStatementParser.getQuery() );
    }

}
