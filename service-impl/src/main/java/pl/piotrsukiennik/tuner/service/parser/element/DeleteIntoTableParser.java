package pl.piotrsukiennik.tuner.service.parser.element;

import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.IntoTableVisitor;
import pl.piotrsukiennik.tuner.model.query.DeleteQuery;
import pl.piotrsukiennik.tuner.model.query.source.TableSource;
import pl.piotrsukiennik.tuner.service.QueryContext;
import pl.piotrsukiennik.tuner.service.parser.ElementParserService;
import pl.piotrsukiennik.tuner.util.NewQueryUtils;

/**
 * Author: Piotr Sukiennik
 * Date: 27.07.13
 * Time: 13:29
 */
public class DeleteIntoTableParser implements IntoTableVisitor {

    private DeleteQuery sourceQuery;

    private QueryContext queryContext;

    public DeleteIntoTableParser( ElementParserService elementParserService, QueryContext queryContext, DeleteQuery sourceQuery ) {
        this.sourceQuery = sourceQuery;
        this.queryContext = queryContext;
    }

    @Override
    public void visit( Table tableName ) {
        TableSource tableSource = new TableSource();
        tableSource.setAlias( NewQueryUtils.getTableAlias( tableName.getAlias() ) );
        tableSource.setValue( NewQueryUtils.getTableSourceValue( tableName ) );
        tableSource.setTable( queryContext.getTable( tableName.getWholeTableName() ) );
        sourceQuery.setTableSource( tableSource );
    }

}
