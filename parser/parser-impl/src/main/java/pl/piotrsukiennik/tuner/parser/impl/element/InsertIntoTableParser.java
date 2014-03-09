package pl.piotrsukiennik.tuner.parser.impl.element;

import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.IntoTableVisitor;
import pl.piotrsukiennik.tuner.model.query.InsertQuery;
import pl.piotrsukiennik.tuner.parser.JsqlParserQueryParsingContext;
import pl.piotrsukiennik.tuner.parser.impl.statement.Visitor;

/**
 * Author: Piotr Sukiennik
 * Date: 27.07.13
 * Time: 13:29
 */
public class InsertIntoTableParser extends Visitor implements IntoTableVisitor {

    private InsertQuery sourceQuery;

    public InsertIntoTableParser( JsqlParserQueryParsingContext parsingContext, InsertQuery sourceQuery ) {
        super( parsingContext );
        this.sourceQuery = sourceQuery;
    }

    @Override
    public void visit( Table tableName ) {
        sourceQuery.setTable( parsingContext.getTable( tableName ) );
    }
}
