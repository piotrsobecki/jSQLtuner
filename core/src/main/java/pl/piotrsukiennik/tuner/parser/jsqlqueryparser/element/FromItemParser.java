package pl.piotrsukiennik.tuner.parser.jsqlqueryparser.element;

import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.FromItemVisitor;
import net.sf.jsqlparser.statement.select.SubJoin;
import net.sf.jsqlparser.statement.select.SubSelect;
import org.springframework.stereotype.Component;
import pl.piotrsukiennik.tuner.persistance.model.query.Query;
import pl.piotrsukiennik.tuner.persistance.model.query.SelectQuery;
import pl.piotrsukiennik.tuner.persistance.model.query.SourcesAware;
import pl.piotrsukiennik.tuner.persistance.model.query.source.Source;
import pl.piotrsukiennik.tuner.persistance.model.query.source.SubQuerySource;
import pl.piotrsukiennik.tuner.persistance.model.query.source.TableSource;
import pl.piotrsukiennik.tuner.persistance.service.ISchemaService;
import pl.piotrsukiennik.tuner.query.QueryContextManager;
import pl.piotrsukiennik.tuner.util.QueryUtils;

/**
 * Author: Piotr Sukiennik
 * Date: 27.07.13
 * Time: 13:24
 */
@Component
public class FromItemParser implements FromItemVisitor {


    private Query sourceQuery;
    private QueryContextManager queryContextManager;

    public FromItemParser(QueryContextManager queryContextManager, Query sourceQuery) {
        this.sourceQuery=sourceQuery;
        this.queryContextManager=queryContextManager;
    }


    @Override
    public void visit(Table tableName) {
        TableSource tableSource = new TableSource();
        tableSource.setAlias(tableName.getAlias());
        tableSource.setValue(tableName.getWholeTableName() + tableName.getAlias() == null ? "" : (" " + tableName.getAlias()));
        tableSource.setTable(queryContextManager.getTable(tableName.getWholeTableName()));
        if (sourceQuery instanceof SourcesAware){
            QueryUtils.addSource((SourcesAware) sourceQuery,tableSource);
        }
    }

    @Override
    public void visit(SubSelect subSelect) {
        SubQuerySource subQuerySource = new SubQuerySource();
        subQuerySource.setAlias(subSelect.getAlias());
        subQuerySource.setValue(subSelect.getSelectBody().toString());

        SelectQuery selectQuery = new SelectQuery();
        selectQuery.setParentQuery(sourceQuery);
        SelectBodyParser<SelectQuery> selectBodyParser = new SelectBodyParser<SelectQuery>(queryContextManager,selectQuery);
        subSelect.getSelectBody().accept(selectBodyParser);
        subQuerySource.setSelectQuery(selectQuery);
        if (sourceQuery instanceof SourcesAware){
            QueryUtils.addSource((SourcesAware) sourceQuery,subQuerySource);
        }
    }

    @Override
    public void visit(SubJoin subjoin) {

    }

}
