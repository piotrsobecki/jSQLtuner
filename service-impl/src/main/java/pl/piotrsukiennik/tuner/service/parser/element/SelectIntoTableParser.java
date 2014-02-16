package pl.piotrsukiennik.tuner.service.parser.element;

import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.IntoTableVisitor;
import pl.piotrsukiennik.tuner.model.query.SelectQuery;
import pl.piotrsukiennik.tuner.model.query.source.TableSource;
import pl.piotrsukiennik.tuner.service.QueryContext;
import pl.piotrsukiennik.tuner.service.parser.QueryElementParserService;
import pl.piotrsukiennik.tuner.service.util.QueryConstructorUtils;

/**
 * Author: Piotr Sukiennik
 * Date: 27.07.13
 * Time: 13:29
 */
public class SelectIntoTableParser implements IntoTableVisitor {

    private SelectQuery sourceQuery;

    private QueryContext queryContext;

    private QueryElementParserService queryElementParserService;

    public SelectIntoTableParser( QueryElementParserService queryElementParserService, QueryContext queryContext, SelectQuery sourceQuery ) {
        this.sourceQuery = sourceQuery;
        this.queryContext = queryContext;
        this.queryElementParserService = queryElementParserService;
    }

    @Override
    public void visit( Table tableName ) {
        TableSource tableSource = queryElementParserService.getTableSource( queryContext, tableName );
        QueryConstructorUtils.addSource( sourceQuery, tableSource );
    }

}
