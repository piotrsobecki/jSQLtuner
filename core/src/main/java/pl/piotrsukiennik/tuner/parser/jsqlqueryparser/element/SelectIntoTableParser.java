package pl.piotrsukiennik.tuner.parser.jsqlqueryparser.element;

import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.IntoTableVisitor;
import pl.piotrsukiennik.tuner.persistance.model.query.InsertQuery;
import pl.piotrsukiennik.tuner.persistance.model.query.SelectQuery;
import pl.piotrsukiennik.tuner.persistance.model.query.source.TableSource;
import pl.piotrsukiennik.tuner.query.QueryContextManager;

/**
 * Author: Piotr Sukiennik
 * Date: 27.07.13
 * Time: 13:29
 */
public class SelectIntoTableParser implements IntoTableVisitor {

    private SelectQuery sourceQuery;
    private QueryContextManager queryContextManager;

    public SelectIntoTableParser(QueryContextManager queryContextManager, SelectQuery sourceQuery) {
        this.sourceQuery=sourceQuery;
        this.queryContextManager=queryContextManager;
    }
    @Override
    public void visit(Table tableName) {
        TableSource tableSource = new TableSource();
        tableSource.setAlias(tableName.getAlias());
        tableSource.setValue(tableName.getWholeTableName() + tableName.getAlias() == null ? "" : (" " + tableName.getAlias()));
        tableSource.setTable(queryContextManager.getTable(tableName.getWholeTableName()));
       // sourceQuery.set(tableSource);
    }

}
