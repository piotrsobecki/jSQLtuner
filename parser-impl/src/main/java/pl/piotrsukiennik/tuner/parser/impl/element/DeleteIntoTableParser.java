package pl.piotrsukiennik.tuner.parser.impl.element;

import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.IntoTableVisitor;
import pl.piotrsukiennik.tuner.model.query.impl.DeleteQuery;
import pl.piotrsukiennik.tuner.parser.JsqlParserQueryParsingContext;

/**
 * Author: Piotr Sukiennik
 * Date: 27.07.13
 * Time: 13:29
 */
public class DeleteIntoTableParser implements IntoTableVisitor {

    private DeleteQuery sourceQuery;

    private JsqlParserQueryParsingContext parsingContext;

    public DeleteIntoTableParser( JsqlParserQueryParsingContext parsingContext, DeleteQuery sourceQuery ) {
        this.sourceQuery = sourceQuery;
        this.parsingContext = parsingContext;
    }

    @Override
    public void visit( Table tableName ) {
        sourceQuery.setTableSource( parsingContext.getTableSource( tableName ) );
    }
}
