package pl.piotrsukiennik.tuner.service.parser.statement;

import net.sf.jsqlparser.statement.create.table.CreateTable;
import pl.piotrsukiennik.tuner.model.query.CreateTableQuery;
import pl.piotrsukiennik.tuner.service.QueryContext;
import pl.piotrsukiennik.tuner.service.parser.ElementParserService;
import pl.piotrsukiennik.tuner.util.NewQueryUtils;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 23:07
 */
public class CreateTableStatementParser extends StatementParser<CreateTableQuery> {
    public CreateTableStatementParser( ElementParserService elementParserService, QueryContext queryContext, CreateTable createTable ) {
        super( elementParserService, queryContext, createTable, new CreateTableQuery() );
    }

    @Override
    public void visit( CreateTable createTable ) {
        query.setTable( NewQueryUtils.map( queryContext, createTable.getTable() ) );
    }


}
