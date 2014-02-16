package pl.piotrsukiennik.tuner.service.parser.statement;

import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import pl.piotrsukiennik.tuner.model.query.SelectQuery;
import pl.piotrsukiennik.tuner.service.QueryContext;
import pl.piotrsukiennik.tuner.service.parser.QueryElementParserService;
import pl.piotrsukiennik.tuner.service.parser.element.SelectBodyParser;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 23:15
 */
public class SelectStatementParser extends StatementParser<SelectQuery> {
    public SelectStatementParser( QueryElementParserService queryElementParserService, QueryContext queryContext, Select select ) {
        super( queryElementParserService, queryContext, select, new SelectQuery() );

    }

    @Override
    public void visit( Select select ) {
        SelectBody selectBody = select.getSelectBody();
        SelectBodyParser<SelectQuery> parsingVisitor = new SelectBodyParser<SelectQuery>( queryElementParserService, getQueryContext(), query );
        selectBody.accept( parsingVisitor );
        super.visit( select );
    }


}
