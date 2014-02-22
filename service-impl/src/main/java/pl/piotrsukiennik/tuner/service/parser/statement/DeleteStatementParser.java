package pl.piotrsukiennik.tuner.service.parser.statement;

import net.sf.jsqlparser.statement.delete.Delete;
import pl.piotrsukiennik.tuner.model.expression.OperatorExpression;
import pl.piotrsukiennik.tuner.model.query.impl.DeleteQuery;
import pl.piotrsukiennik.tuner.model.source.TableSource;
import pl.piotrsukiennik.tuner.service.parser.QueryParsingContext;
import pl.piotrsukiennik.tuner.service.parser.element.ExpresionParser;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 23:07
 */
public class DeleteStatementParser extends StatementParser<DeleteQuery> {
    public DeleteStatementParser( QueryParsingContext parsingContext, Delete delete ) {
        super( parsingContext, delete, new DeleteQuery() );
    }


    @Override
    public void visit( Delete delete ) {
        TableSource tableSource = parsingContext.getTableSource( delete.getTable() );
        query.setTableSource( tableSource );
        if ( delete.getWhere() != null ) {
            ExpresionParser expresionParser = new ExpresionParser( parsingContext );
            delete.getWhere().accept( expresionParser );
            query.setWhereExpression( (OperatorExpression) expresionParser.getExpression() );
        }
        super.visit( delete );
    }


}
