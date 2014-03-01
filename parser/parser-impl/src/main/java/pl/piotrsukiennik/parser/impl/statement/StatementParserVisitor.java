package pl.piotrsukiennik.parser.impl.statement;

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
import pl.piotrsukiennik.parser.QueryParsingContext;
import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.model.query.impl.UpdateQuery;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 22:38
 */
public class StatementParserVisitor<T extends Query> extends StatementParser implements StatementVisitor {


    public StatementParserVisitor( QueryParsingContext parsingContext, Statement statement ) {
        super( parsingContext, statement );
    }

    @Override
    public void visit( Replace replace ) {
        ReplaceStatementParser statementParser = new ReplaceStatementParser( parsingContext, replace );
        setQuery( statementParser.getQuery() );
    }

    @Override
    public void visit( Truncate truncate ) {
        TruncateTableStatementParser statementParser = new TruncateTableStatementParser( parsingContext, truncate );
        setQuery( statementParser.getQuery() );
    }

    @Override
    public void visit( CreateTable createTable ) {
        CreateTableStatementParser statementParser = new CreateTableStatementParser( parsingContext, createTable );
        setQuery( statementParser.getQuery() );
    }

    @Override
    public void visit( Drop drop ) {
        DropStatementParser statementParser = new DropStatementParser( parsingContext, drop );
        setQuery( statementParser.getQuery() );
    }

    @Override
    public void visit( Select select ) {
        SelectStatementParser statementParser = new SelectStatementParser( parsingContext, select );
        setQuery( statementParser.getQuery() );
    }

    @Override
    public void visit( Delete delete ) {
        DeleteStatementParser deleteStatementParser = new DeleteStatementParser( parsingContext, delete );
        setQuery( deleteStatementParser.getQuery() );
    }

    @Override
    public void visit( Update update ) {
        UpdateStatementParser<UpdateQuery> updateStatementParser = new UpdateStatementParser<UpdateQuery>( parsingContext, update );
        setQuery( updateStatementParser.getQuery() );
    }

    @Override
    public void visit( Insert insert ) {
        InsertStatementParser insertStatementParser = new InsertStatementParser( parsingContext, insert );
        setQuery( insertStatementParser.getQuery() );
    }

}
