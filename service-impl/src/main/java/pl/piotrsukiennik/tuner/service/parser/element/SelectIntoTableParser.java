package pl.piotrsukiennik.tuner.service.parser.element;

import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.IntoTableVisitor;
import pl.piotrsukiennik.tuner.model.query.SelectQuery;
import pl.piotrsukiennik.tuner.model.query.source.Source;
import pl.piotrsukiennik.tuner.model.query.source.TableSource;
import pl.piotrsukiennik.tuner.service.QueryElementParserService;
import pl.piotrsukiennik.tuner.service.query.QueryContext;
import pl.piotrsukiennik.tuner.service.util.CollectionUtils;

import java.util.Set;

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
        TableSource tableSource = new TableSource();
        tableSource.setAlias( tableName.getAlias() );
        tableSource.setValue( tableName.getWholeTableName() + tableName.getAlias() == null ? "" : ( " " + tableName.getAlias() ) );
        tableSource.setTable( queryContext.getTable( tableName.getWholeTableName() ) );
        Set<Source> sourceSet = CollectionUtils.createNewLinkedHashSetIfNull( sourceQuery.getSources() );
        sourceSet.add( tableSource );
        sourceQuery.setSources( sourceSet );
    }

}
