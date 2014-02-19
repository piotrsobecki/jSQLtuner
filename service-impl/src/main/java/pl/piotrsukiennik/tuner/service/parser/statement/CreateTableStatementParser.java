package pl.piotrsukiennik.tuner.service.parser.statement;

import net.sf.jsqlparser.statement.create.index.CreateIndex;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.create.view.CreateView;
import pl.piotrsukiennik.tuner.model.query.create.CreateIndexQuery;
import pl.piotrsukiennik.tuner.model.query.create.CreateQuery;
import pl.piotrsukiennik.tuner.model.query.create.CreateTableQuery;
import pl.piotrsukiennik.tuner.model.query.create.CreateViewQuery;
import pl.piotrsukiennik.tuner.model.schema.Index;
import pl.piotrsukiennik.tuner.model.schema.Table;
import pl.piotrsukiennik.tuner.service.QueryContext;
import pl.piotrsukiennik.tuner.service.parser.ElementParserService;
import pl.piotrsukiennik.tuner.util.NewQueryUtils;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 23:07
 */
public class CreateTableStatementParser extends StatementParser<CreateQuery> {
    public CreateTableStatementParser( ElementParserService elementParserService, QueryContext queryContext, CreateTable createTable ) {
        super( elementParserService, queryContext, createTable );
    }

    @Override
    public void visit( CreateTable createTable ) {
        query = new CreateTableQuery();
        Table table = NewQueryUtils.map( queryContext, createTable.getTable() );
        ( (CreateTableQuery) query ).setTable( table );
    }

    @Override
    public void visit( CreateIndex createIndex ) {
        query = new CreateIndexQuery();
        Table table = NewQueryUtils.map( queryContext, createIndex.getTable() );
        Index index = NewQueryUtils.map( queryContext, table, createIndex.getIndex() );
        ( (CreateIndexQuery) query ).setTable( table );
        ( (CreateIndexQuery) query ).setIndex( index );
    }

    @Override
    public void visit( CreateView createView ) {
        query = new CreateViewQuery();
        Table table = NewQueryUtils.map( queryContext, createView.getView() );
        ( (CreateViewQuery) query ).setView( table );
    }

}
