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
import pl.piotrsukiennik.tuner.service.QueryContext;
import pl.piotrsukiennik.tuner.service.parser.ElementParserService;


/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 23:06
 */
public abstract class StatementParser<T extends Query> extends ParsingVisitor<T> implements StatementVisitor {


    protected StatementParser( ElementParserService elementParserService, QueryContext queryContext, Statement statement, T query ) {
        super( elementParserService, queryContext, query );
        init( statement );
        statement.accept( this );
    }

    @Override
    public void visit( Select select ) {
    }

    @Override
    public void visit( Delete delete ) {
    }

    @Override
    public void visit( Update update ) {
    }

    @Override
    public void visit( Insert insert ) {
    }

    @Override
    public void visit( Replace replace ) {
    }

    @Override
    public void visit( Drop drop ) {
    }

    @Override
    public void visit( Truncate truncate ) {
    }

    @Override
    public void visit( CreateTable createTable ) {
    }
}
