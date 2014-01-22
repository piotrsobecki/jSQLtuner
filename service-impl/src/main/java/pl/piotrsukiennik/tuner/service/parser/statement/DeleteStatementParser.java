package pl.piotrsukiennik.tuner.service.parser.statement;

import net.sf.jsqlparser.statement.delete.Delete;
import pl.piotrsukiennik.tuner.model.query.DeleteQuery;
import pl.piotrsukiennik.tuner.model.query.source.TableSource;
import pl.piotrsukiennik.tuner.service.QueryElementParserService;
import pl.piotrsukiennik.tuner.service.parser.element.ExpresionParser;
import pl.piotrsukiennik.tuner.service.query.QueryContext;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 23:07
 */
public class DeleteStatementParser extends StatementParser<DeleteQuery> {
    public DeleteStatementParser( QueryElementParserService queryElementParserService, QueryContext queryContext, Delete delete ) {
        super( queryElementParserService, queryContext, delete, new DeleteQuery() );
    }

    @Override
    public void visit( Delete delete ) {
        TableSource tableSource = queryElementParserService.getTableSource( queryContext, delete.getTable() );
        query.setTableSource( tableSource );
        if ( delete.getWhere() != null ) {
            ExpresionParser expresionParser = new ExpresionParser( queryElementParserService, queryContext );
            delete.getWhere().accept( expresionParser );
            query.setWhereCondition( expresionParser.getCondition() );
        }
        super.visit( delete );
    }


}
