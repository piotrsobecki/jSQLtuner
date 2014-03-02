package pl.piotrsukiennik.tuner.parser.impl.statement;

import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import pl.piotrsukiennik.tuner.model.query.impl.SelectQuery;
import pl.piotrsukiennik.tuner.parser.QueryParsingContext;
import pl.piotrsukiennik.tuner.parser.impl.element.SelectBodyParser;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 23:15
 */
public class SelectStatementParser extends StatementParser<SelectQuery> {
    public SelectStatementParser( QueryParsingContext parsingContext, Select select ) {
        super( parsingContext, select, new SelectQuery() );

    }

    @Override
    public void visit( Select select ) {
        SelectBody selectBody = select.getSelectBody();
        SelectBodyParser<SelectQuery> parsingVisitor = new SelectBodyParser<SelectQuery>( parsingContext, query );
        selectBody.accept( parsingVisitor );
        super.visit( select );
    }


}
