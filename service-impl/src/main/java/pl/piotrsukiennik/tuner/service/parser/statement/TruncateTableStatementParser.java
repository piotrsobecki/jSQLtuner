package pl.piotrsukiennik.tuner.service.parser.statement;

import net.sf.jsqlparser.statement.truncate.Truncate;
import pl.piotrsukiennik.tuner.model.query.TruncateQuery;
import pl.piotrsukiennik.tuner.service.QueryContext;
import pl.piotrsukiennik.tuner.service.parser.ElementParserService;
import pl.piotrsukiennik.tuner.util.NewQueryUtils;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 23:07
 */
public class TruncateTableStatementParser extends StatementParser<TruncateQuery> {
    public TruncateTableStatementParser( ElementParserService elementParserService, QueryContext queryContext, Truncate truncate ) {
        super( elementParserService, queryContext, truncate, new TruncateQuery() );
    }

    @Override
    public void visit( Truncate truncate ) {
        query.setTable( NewQueryUtils.map( queryContext, truncate.getTable() ) );
    }


}
