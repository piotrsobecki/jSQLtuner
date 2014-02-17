package pl.piotrsukiennik.tuner.service.parser.element;

import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.IntoTableVisitor;
import pl.piotrsukiennik.tuner.model.query.SelectQuery;
import pl.piotrsukiennik.tuner.model.query.source.TableSource;
import pl.piotrsukiennik.tuner.service.QueryContext;
import pl.piotrsukiennik.tuner.service.parser.ElementParserService;
import pl.piotrsukiennik.tuner.util.NewQueryUtils;

/**
 * Author: Piotr Sukiennik
 * Date: 27.07.13
 * Time: 13:29
 */
public class SelectIntoTableParser implements IntoTableVisitor {

    private SelectQuery sourceQuery;

    private QueryContext queryContext;

    private ElementParserService elementParserService;

    public SelectIntoTableParser( ElementParserService elementParserService, QueryContext queryContext, SelectQuery sourceQuery ) {
        this.sourceQuery = sourceQuery;
        this.queryContext = queryContext;
        this.elementParserService = elementParserService;
    }

    @Override
    public void visit( Table tableName ) {
        TableSource tableSource = elementParserService.getTableSource( queryContext, tableName );
        NewQueryUtils.addSource( sourceQuery, tableSource );
    }

}
