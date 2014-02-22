package pl.piotrsukiennik.tuner.service.parser.statement;

import net.sf.jsqlparser.statement.replace.Replace;
import pl.piotrsukiennik.tuner.model.query.impl.ReplaceQuery;
import pl.piotrsukiennik.tuner.service.parser.QueryParsingContext;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 23:07
 */
public class ReplaceStatementParser extends StatementParser<ReplaceQuery> {
    public ReplaceStatementParser( QueryParsingContext parsingContext, Replace replace ) {
        super( parsingContext, replace, new ReplaceQuery() );
    }

    @Override
    public void visit( Replace replace ) {
        query.setTable( parsingContext.getTable( replace.getTable() ) );
        super.visit( replace );
    }


}
