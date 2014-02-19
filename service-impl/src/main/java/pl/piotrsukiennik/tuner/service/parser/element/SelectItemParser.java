package pl.piotrsukiennik.tuner.service.parser.element;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.AllColumns;
import net.sf.jsqlparser.statement.select.AllTableColumns;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItemVisitor;
import pl.piotrsukiennik.tuner.model.expression.projection.StarProjection;
import pl.piotrsukiennik.tuner.model.query.ProjectionsAwareQuery;
import pl.piotrsukiennik.tuner.model.source.Source;
import pl.piotrsukiennik.tuner.service.QueryContext;
import pl.piotrsukiennik.tuner.service.parser.ElementParserService;
import pl.piotrsukiennik.tuner.util.NewQueryUtils;

import java.util.LinkedHashSet;

/**
 * Author: Piotr Sukiennik
 * Date: 14.09.13
 * Time: 02:23
 */
public class SelectItemParser<T extends ProjectionsAwareQuery> implements SelectItemVisitor {

    private T selectQuery;

    private QueryContext queryContext;

    private ElementParserService elementParserService;

    public SelectItemParser( ElementParserService elementParserService, QueryContext queryContext, T selectQuery ) {
        this.selectQuery = selectQuery;
        this.queryContext = queryContext;
        this.elementParserService = elementParserService;
        if ( this.selectQuery.getProjections() == null ) {
            this.selectQuery.setProjections( new LinkedHashSet<pl.piotrsukiennik.tuner.model.expression.Expression>() );
        }
    }

    @Override
    public void visit( AllColumns allColumns ) {
        for ( Source source : selectQuery.getSources() ) {
            StarProjection starProjection = elementParserService.getStarProjection( queryContext, allColumns, source );
            NewQueryUtils.addProjection( selectQuery, starProjection );
        }
    }

    @Override
    public void visit( AllTableColumns allTableColumns ) {
        StarProjection starProjection = elementParserService.getStarProjection( queryContext, allTableColumns );
        NewQueryUtils.addProjection( selectQuery, starProjection );
    }

    @Override
    public void visit( SelectExpressionItem selectExpressionItem ) {
        Expression expression = selectExpressionItem.getExpression();
        if ( expression instanceof Column ) {
            Column column = ( (Column) expression );
            NewQueryUtils.addProjection( selectQuery, elementParserService.getColumnProjection( queryContext, column ) );
        }
    }
}
