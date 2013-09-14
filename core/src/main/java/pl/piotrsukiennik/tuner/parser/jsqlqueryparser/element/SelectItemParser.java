package pl.piotrsukiennik.tuner.parser.jsqlqueryparser.element;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.AllColumns;
import net.sf.jsqlparser.statement.select.AllTableColumns;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItemVisitor;
import org.hibernate.loader.ColumnEntityAliases;
import pl.piotrsukiennik.tuner.persistance.model.query.ProjectionsAware;
import pl.piotrsukiennik.tuner.persistance.model.query.SelectQuery;
import pl.piotrsukiennik.tuner.persistance.model.query.projection.ColumnProjection;
import pl.piotrsukiennik.tuner.persistance.model.query.projection.Projection;
import pl.piotrsukiennik.tuner.persistance.model.query.projection.StarProjection;
import pl.piotrsukiennik.tuner.persistance.model.query.source.Source;
import pl.piotrsukiennik.tuner.persistance.model.query.source.TableSource;
import pl.piotrsukiennik.tuner.persistance.model.schema.Table;
import pl.piotrsukiennik.tuner.query.QueryContextManager;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Author: Piotr Sukiennik
 * Date: 14.09.13
 * Time: 02:23
 */
public class SelectItemParser<T extends ProjectionsAware> implements SelectItemVisitor {

    private T selectQuery;
    private QueryContextManager queryContextManager;

    public SelectItemParser(QueryContextManager queryContextManager,T selectQuery) {
        this.selectQuery = selectQuery;
        this.queryContextManager=queryContextManager;
        if (this.selectQuery.getProjections() == null){
            this.selectQuery.setProjections(new LinkedHashSet<Projection>());
        }
    }

    @Override
    public void visit(AllColumns allColumns) {
        for (Source source : selectQuery.getSources()){
            StarProjection starProjection = new StarProjection();
            starProjection.setSource(source);
            addProjection(starProjection);
        }
    }

    @Override
    public void visit(AllTableColumns allTableColumns) {
        StarProjection starProjection = new StarProjection();
        Table table =  queryContextManager.getTable(allTableColumns.getTable().getWholeTableName());
        for (Source source: selectQuery.getSources()){
            if (source instanceof TableSource &&  ((TableSource)source).getTable().equals(table)){
                starProjection.setSource(source);
            }
        }
        addProjection(starProjection);
    }

    @Override
    public void visit(SelectExpressionItem selectExpressionItem) {
        Expression expression = selectExpressionItem.getExpression();
        if (expression instanceof Column){
            ColumnProjection columnProjection = new ColumnProjection();
            Column column  = ((Column)expression);
            TableSource tableSource = queryContextManager.getTableSource(column.getTable().getWholeTableName());
            pl.piotrsukiennik.tuner.persistance.model.schema.Column columnPersisted
                    = queryContextManager.getColumn(tableSource.getTable().getValue(),column.getColumnName());
            columnProjection.setSource(tableSource);
            columnProjection.setColumn(columnPersisted);
            addProjection(columnProjection);
        }
    }

    protected void addProjection(Projection projection){
        projection.setPosition(selectQuery.getSources().size());
        selectQuery.getProjections().add(projection);
    }
}
