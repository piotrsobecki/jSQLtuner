package pl.piotrsukiennik.tuner.parser.jsqlqueryparser;

import net.sf.jsqlparser.schema.Table;
import pl.piotrsukiennik.tuner.persistance.model.query.SourcesAware;
import pl.piotrsukiennik.tuner.persistance.model.query.source.TableSource;
import pl.piotrsukiennik.tuner.query.QueryContextManager;
import pl.piotrsukiennik.tuner.util.QueryUtils;

/**
 * Author: Piotr Sukiennik
 * Date: 14.09.13
 * Time: 18:07
 */
public class ParserUtils {

    private QueryContextManager queryContextManager;

    public ParserUtils(QueryContextManager queryContextManager){
        this.queryContextManager=queryContextManager;
    }

    public TableSource getTableSource(Table tableName) {
        TableSource tableSource = new TableSource();
        tableSource.setAlias(tableName.getAlias());
        tableSource.setValue(tableName.getWholeTableName() + (tableName.getAlias() == null ? "" : (" " + tableName.getAlias())));
        tableSource.setTable(queryContextManager.getTable(tableName.getWholeTableName()));
        queryContextManager.putTableSource(tableSource);
        return tableSource;
    }

    public TableSource getTableSource(net.sf.jsqlparser.schema.Column column) {
        return queryContextManager.getTableSource(column.getTable().getWholeTableName());
    }

}
