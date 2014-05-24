package pl.piotrsukiennik.tuner.parser.impl.statement;

import net.sf.jsqlparser.statement.truncate.Truncate;
import pl.piotrsukiennik.tuner.model.query.impl.TruncateQuery;
import pl.piotrsukiennik.tuner.parser.JsqlParserQueryParsingContext;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 23:07
 */
public class TruncateTableStatementParser extends StatementParser<TruncateQuery> {
    public TruncateTableStatementParser( JsqlParserQueryParsingContext parsingContext, Truncate truncate ) {
        super( parsingContext, truncate, new TruncateQuery() );
    }

    @Override
    public void visit( Truncate truncate ) {
        query.setTable( parsingContext.getTable( truncate.getTable() ) );
    }


}
