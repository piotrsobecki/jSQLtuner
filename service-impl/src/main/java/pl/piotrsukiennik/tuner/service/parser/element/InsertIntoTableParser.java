package pl.piotrsukiennik.tuner.service.parser.element;

import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.IntoTableVisitor;
import pl.piotrsukiennik.tuner.model.query.InsertQuery;
import pl.piotrsukiennik.tuner.service.QueryContext;

/**
 * Author: Piotr Sukiennik
 * Date: 27.07.13
 * Time: 13:29
 */
public class InsertIntoTableParser implements IntoTableVisitor {

    private InsertQuery sourceQuery;

    private QueryContext queryContext;


    public InsertIntoTableParser( QueryContext queryContext, InsertQuery sourceQuery ) {
        this.sourceQuery = sourceQuery;
        this.queryContext = queryContext;
    }

    @Override
    public void visit( Table tableName ) {
        pl.piotrsukiennik.tuner.model.schema.Table table = queryContext.getTable( tableName.getWholeTableName() );
        sourceQuery.setTable( table );
    }
}
