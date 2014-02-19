package pl.piotrsukiennik.tuner.service.parser.statement;

import net.sf.jsqlparser.statement.replace.Replace;
import pl.piotrsukiennik.tuner.model.query.impl.ReplaceQuery;
import pl.piotrsukiennik.tuner.service.QueryContext;
import pl.piotrsukiennik.tuner.service.parser.ElementParserService;
import pl.piotrsukiennik.tuner.util.NewQueryUtils;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 23:07
 */
public class ReplaceStatementParser extends StatementParser<ReplaceQuery> {
    public ReplaceStatementParser( ElementParserService elementParserService, QueryContext queryContext, Replace replace ) {
        super( elementParserService, queryContext, replace, new ReplaceQuery() );
    }

    @Override
    public void visit( Replace replace ) {
        query.setTable( NewQueryUtils.map( queryContext, replace.getTable() ) );
        super.visit( replace );
    }


}
