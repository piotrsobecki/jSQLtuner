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
import pl.piotrsukiennik.tuner.service.QueryContext;
import pl.piotrsukiennik.tuner.service.parser.QueryElementParserService;
import pl.piotrsukiennik.tuner.service.util.QueryConstructorUtils;

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
            StarProjection starProjection = queryElementParserService.getStarProjection( queryContext, allColumns, source );
            QueryConstructorUtils.addProjection( selectQuery, starProjection );
        }
    }

    @Override
    public void visit( AllTableColumns allTableColumns ) {
        StarProjection starProjection = queryElementParserService.getStarProjection( queryContext, allTableColumns );
        QueryConstructorUtils.addProjection( selectQuery, starProjection );
    }

    @Override
    public void visit( SelectExpressionItem selectExpressionItem ) {
        Expression expression = selectExpressionItem.getExpression();
        if ( expression instanceof Column ) {
            Column column = ( (Column) expression );
            QueryConstructorUtils.addProjection( selectQuery, queryElementParserService.getColumnProjection( queryContext, column ) );
        }
    }
}
