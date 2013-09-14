package pl.piotrsukiennik.tuner.parser.jsqlqueryparser.element;

import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.FromItemVisitor;
import net.sf.jsqlparser.statement.select.SubJoin;
import net.sf.jsqlparser.statement.select.SubSelect;
import pl.piotrsukiennik.tuner.parser.jsqlqueryparser.statement.Visitor;
import pl.piotrsukiennik.tuner.persistance.model.query.Query;
import pl.piotrsukiennik.tuner.persistance.model.query.SelectQuery;
import pl.piotrsukiennik.tuner.persistance.model.query.SourcesAware;
import pl.piotrsukiennik.tuner.persistance.model.query.source.SubQuerySource;
import pl.piotrsukiennik.tuner.persistance.model.query.source.TableSource;
import pl.piotrsukiennik.tuner.query.QueryContextManager;
import pl.piotrsukiennik.tuner.util.QueryUtils;

/**
 * Author: Piotr Sukiennik
 * Date: 27.07.13
 * Time: 13:24
 */
public class FromItemParser extends Visitor implements FromItemVisitor {


    private Query sourceQuery;

    public FromItemParser(QueryContextManager queryContextManager, Query sourceQuery) {
        super(queryContextManager);
        this.sourceQuery=sourceQuery;
    }


    @Override
    public void visit(Table tableName) {
        TableSource tableSource = parserUtils.getTableSource(tableName);
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
