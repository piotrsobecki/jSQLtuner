package pl.piotrsukiennik.tuner.parser.impl.statement;

import net.sf.jsqlparser.statement.create.index.CreateIndex;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.create.view.CreateView;
import pl.piotrsukiennik.tuner.model.query.CreateQuery;
import pl.piotrsukiennik.tuner.model.query.impl.CreateIndexQuery;
import pl.piotrsukiennik.tuner.model.query.impl.CreateTableQuery;
import pl.piotrsukiennik.tuner.model.query.impl.CreateViewQuery;
import pl.piotrsukiennik.tuner.model.schema.Index;
import pl.piotrsukiennik.tuner.model.schema.Table;
import pl.piotrsukiennik.tuner.parser.QueryParsingContext;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 23:07
 */
public class CreateTableStatementParser extends StatementParser<CreateQuery> {
    public CreateTableStatementParser( QueryParsingContext parsingContext, CreateTable createTable ) {
        super( parsingContext, createTable );
    }

    @Override
    public void visit( CreateTable createTable ) {
        query = new CreateTableQuery();
        Table table = parsingContext.getTable( createTable.getTable() );
        ( (CreateTableQuery) query ).setTable( table );
    }

    @Override
    public void visit( CreateIndex createIndex ) {
        query = new CreateIndexQuery();
        Table table = parsingContext.getTable( createIndex.getTable() );
        Index index = parsingContext.getIndex( table, createIndex.getIndex() );
        ( (CreateIndexQuery) query ).setTable( table );
        ( (CreateIndexQuery) query ).setIndex( index );
    }

    @Override
    public void visit( CreateView createView ) {
        query = new CreateViewQuery();
        Table table = parsingContext.getTable( createView.getView() );
        ( (CreateViewQuery) query ).setView( table );
    }

}
