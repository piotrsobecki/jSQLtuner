package pl.piotrsukiennik.tuner.service.parser.element;

import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.IntoTableVisitor;
import pl.piotrsukiennik.tuner.model.query.InsertQuery;
import pl.piotrsukiennik.tuner.service.parser.QueryParsingContext;
import pl.piotrsukiennik.tuner.service.parser.statement.Visitor;

/**
 * Author: Piotr Sukiennik
 * Date: 27.07.13
 * Time: 13:29
 */
public class InsertIntoTableParser extends Visitor implements IntoTableVisitor {

    private InsertQuery sourceQuery;

    public InsertIntoTableParser( QueryParsingContext parsingContext, InsertQuery sourceQuery ) {
        super( parsingContext );
        this.sourceQuery = sourceQuery;
    }

    @Override
    public void visit( Table tableName ) {
        sourceQuery.setTable( parsingContext.getTable( tableName ) );
    }
}
