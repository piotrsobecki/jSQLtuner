package pl.piotrsukiennik.tuner.parser.impl.element;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.AllColumns;
import net.sf.jsqlparser.statement.select.AllTableColumns;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItemVisitor;
import pl.piotrsukiennik.tuner.model.expression.projection.StarProjection;
import pl.piotrsukiennik.tuner.model.query.ProjectionsAwareQuery;
import pl.piotrsukiennik.tuner.model.source.Source;
import pl.piotrsukiennik.tuner.parser.NewQueryUtils;
import pl.piotrsukiennik.tuner.parser.QueryParsingContext;
import pl.piotrsukiennik.tuner.parser.impl.statement.Visitor;

import java.util.LinkedHashSet;

/**
 * Author: Piotr Sukiennik
 * Date: 14.09.13
 * Time: 02:23
 */
public class SelectItemParser<T extends ProjectionsAwareQuery> extends Visitor implements SelectItemVisitor {

    private T selectQuery;


    public SelectItemParser( QueryParsingContext parsingContext, T selectQuery ) {
        super( parsingContext );
        this.selectQuery = selectQuery;
        if ( this.selectQuery.getProjections() == null ) {
            this.selectQuery.setProjections( new LinkedHashSet<pl.piotrsukiennik.tuner.model.expression.Expression>() );
        }
    }

    @Override
    public void visit( AllColumns allColumns ) {
        for ( Source source : selectQuery.getSources() ) {
            StarProjection starProjection = parsingContext.getStarProjection( source );
            NewQueryUtils.addProjection( selectQuery, starProjection );
        }
    }

    @Override
    public void visit( AllTableColumns allTableColumns ) {
        StarProjection starProjection = parsingContext.getStarProjection( allTableColumns );
        NewQueryUtils.addProjection( selectQuery, starProjection );
    }

    @Override
    public void visit( SelectExpressionItem selectExpressionItem ) {
        Expression expression = selectExpressionItem.getExpression();
        if ( expression instanceof Column ) {
            Column column = ( (Column) expression );
            NewQueryUtils.addProjection( selectQuery, parsingContext.getColumnProjection( column ) );
        }
    }
}
