package pl.piotrsukiennik.parser.impl.element;

import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.IntoTableVisitor;
import pl.piotrsukiennik.parser.QueryParsingContext;
import pl.piotrsukiennik.tuner.model.query.impl.DeleteQuery;

/**
 * Author: Piotr Sukiennik
 * Date: 27.07.13
 * Time: 13:29
 */
public class DeleteIntoTableParser implements IntoTableVisitor {

    private DeleteQuery sourceQuery;

    private QueryParsingContext parsingContext;

    public DeleteIntoTableParser( QueryParsingContext parsingContext, DeleteQuery sourceQuery ) {
        this.sourceQuery = sourceQuery;
        this.parsingContext = parsingContext;
    }

    @Override
    public void visit( Table tableName ) {
        sourceQuery.setTableSource( parsingContext.getTableSource( tableName ) );
    }
}
