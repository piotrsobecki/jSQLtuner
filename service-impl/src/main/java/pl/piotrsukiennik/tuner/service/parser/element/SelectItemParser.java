package pl.piotrsukiennik.tuner.service.parser.element;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.AllColumns;
import net.sf.jsqlparser.statement.select.AllTableColumns;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItemVisitor;
import pl.piotrsukiennik.tuner.model.query.ProjectionsAware;
import pl.piotrsukiennik.tuner.model.query.projection.Projection;
import pl.piotrsukiennik.tuner.model.query.projection.StarProjection;
import pl.piotrsukiennik.tuner.model.query.source.Source;
import pl.piotrsukiennik.tuner.model.query.source.TableSource;
import pl.piotrsukiennik.tuner.model.schema.Table;
import pl.piotrsukiennik.tuner.service.QueryElementParserService;
import pl.piotrsukiennik.tuner.service.query.QueryContext;
import pl.piotrsukiennik.tuner.service.util.QueryUtils;

import java.util.LinkedHashSet;

/**
 * Author: Piotr Sukiennik
 * Date: 14.09.13
 * Time: 02:23
 */
public class SelectItemParser<T extends ProjectionsAware> implements SelectItemVisitor {

    private T selectQuery;

    private QueryContext queryContext;

    private QueryElementParserService queryElementParserService;

    public SelectItemParser( QueryElementParserService queryElementParserService, QueryContext queryContext, T selectQuery ) {
        this.selectQuery = selectQuery;
        this.queryContext = queryContext;
        this.queryElementParserService = queryElementParserService;
        if ( this.selectQuery.getProjections() == null ) {
            this.selectQuery.setProjections( new LinkedHashSet<Projection>() );
        }
    }

    @Override
    public void visit( AllColumns allColumns ) {
        for ( Source source : selectQuery.getSources() ) {
            StarProjection starProjection = new StarProjection();
            starProjection.setSource( source );
            QueryUtils.addProjection( selectQuery, starProjection );
        }
    }

    @Override
    public void visit( AllTableColumns allTableColumns ) {
        StarProjection starProjection = new StarProjection();
        Table table = queryContext.getTable( allTableColumns.getTable().getWholeTableName() );
        for ( Source source : selectQuery.getSources() ) {
            if ( source instanceof TableSource && ( (TableSource) source ).getTable().equals( table ) ) {
                starProjection.setSource( source );
            }
        }
        QueryUtils.addProjection( selectQuery, starProjection );
    }

    @Override
    public void visit( SelectExpressionItem selectExpressionItem ) {
        Expression expression = selectExpressionItem.getExpression();
        if ( expression instanceof Column ) {
            Column column = ( (Column) expression );
            QueryUtils.addProjection( selectQuery, queryElementParserService.getColumnProjection( queryContext, column ) );
           /* ColumnProjection columnProjection = new ColumnProjection();
            TableSource tableSource = queryContextDto.getTableSource( column.getTable().getWholeTableName() );
            pl.piotrsukiennik.tuner.model.schema.Column columnPersisted = queryContextDto.getColumn( tableSource.getTable(), column.getColumnName() );
            columnProjection.setSource( tableSource );
            columnProjection.setColumn( columnPersisted );
            addProjection( columnProjection );*/
        }
    }
}
