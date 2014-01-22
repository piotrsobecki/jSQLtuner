package pl.piotrsukiennik.tuner.service.parser.element;

import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.IntoTableVisitor;
import pl.piotrsukiennik.tuner.model.query.InsertQuery;
import pl.piotrsukiennik.tuner.service.QueryElementParserService;
import pl.piotrsukiennik.tuner.service.query.QueryContext;

/**
 * Author: Piotr Sukiennik
 * Date: 27.07.13
 * Time: 13:29
 */
public class InsertIntoTableParser implements IntoTableVisitor {

    private InsertQuery sourceQuery;

    private QueryContext queryContext;

    private QueryElementParserService queryElementParserService;

    public InsertIntoTableParser( QueryElementParserService queryElementParserService, QueryContext queryContext, InsertQuery sourceQuery ) {
        this.sourceQuery = sourceQuery;
        this.queryContext = queryContext;
        this.queryElementParserService = queryElementParserService;
    }

    @Override
    public void visit( Table tableName ) {
        throw new RuntimeException( "NOT SUPPORTED" );
/*
        TableSource tableSource = new TableSource();
        tableSource.setAlias(tableName.getAlias());
        tableSource.setValue(tableName.getWholeTableName() + tableName.getAlias() == null ? "" : (" " + tableName.getAlias()));
        tableSource.setTable(queryContextDto.getTable(tableName.getWholeTableName()));
        sourceQuery.setTable(tableSource);*/
    }

}
