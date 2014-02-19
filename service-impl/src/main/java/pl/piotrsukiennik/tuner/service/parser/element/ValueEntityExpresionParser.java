package pl.piotrsukiennik.tuner.service.parser.element;

import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.expression.operators.arithmetic.*;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.*;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.SubSelect;
import pl.piotrsukiennik.tuner.model.ValueEntity;
import pl.piotrsukiennik.tuner.model.schema.Database;
import pl.piotrsukiennik.tuner.model.schema.Schema;
import pl.piotrsukiennik.tuner.model.schema.Table;
import pl.piotrsukiennik.tuner.service.QueryContext;
import pl.piotrsukiennik.tuner.service.parser.ElementParserService;

/**
 * Author: Piotr Sukiennik
 * Date: 27.07.13
 * Time: 13:25
 */
public class ValueEntityExpresionParser implements ExpressionVisitor {

    private ValueEntity valueEntity;

    private QueryContext queryContext;

    private ElementParserService elementParserService;

    public ValueEntityExpresionParser( ElementParserService elementParserService, QueryContext queryContext, ValueEntity valueEntity ) {
        this.valueEntity = valueEntity;
        this.queryContext = queryContext;
        this.elementParserService = elementParserService;
    }


    @Override
    public void visit( NullValue nullValue ) {
        valueEntity.setValue( null );
    }

    @Override
    public void visit( Function function ) {
        valueEntity.setValue( function.toString() );
    }

    @Override
    public void visit( InverseExpression inverseExpression ) {
        ValueEntityExpresionParser valueEntityExpresionParser = new ValueEntityExpresionParser( elementParserService, queryContext, valueEntity );
        inverseExpression.getExpression().accept( valueEntityExpresionParser );
        valueEntity.setValue( "Inverse " + valueEntity.getValue() );
    }

    @Override
    public void visit( JdbcParameter jdbcParameter ) {
        valueEntity.setValue( jdbcParameter.toString() );

    }

    @Override
    public void visit( DoubleValue doubleValue ) {
        valueEntity.setValue( Double.toString( doubleValue.getValue() ) );

    }

    @Override
    public void visit( LongValue longValue ) {
        valueEntity.setValue( Long.toString( longValue.getValue() ) );

    }

    @Override
    public void visit( DateValue dateValue ) {
        valueEntity.setValue( dateValue.getValue().toString() );

    }

    @Override
    public void visit( TimeValue timeValue ) {
        valueEntity.setValue( timeValue.getValue().toString() );

    }

    @Override
    public void visit( TimestampValue timestampValue ) {
        valueEntity.setValue( timestampValue.getValue().toString() );

    }

    @Override
    public void visit( Parenthesis parenthesis ) {
        valueEntity.setValue( parenthesis.toString() );
    }

    @Override
    public void visit( StringValue stringValue ) {
        valueEntity.setValue( stringValue.getValue() );
    }

    @Override
    public void visit( Addition addition ) {
        valueEntity.setValue( addition.getStringExpression() );

    }

    @Override
    public void visit( Division division ) {
        valueEntity.setValue( division.getStringExpression() );

    }

    @Override
    public void visit( Multiplication multiplication ) {
        valueEntity.setValue( multiplication.getStringExpression() );

    }

    @Override
    public void visit( Subtraction subtraction ) {
        valueEntity.setValue( subtraction.getStringExpression() );

    }

    @Override
    public void visit( AndExpression andExpression ) {
        valueEntity.setValue( andExpression.getStringExpression() );

    }

    @Override
    public void visit( OrExpression orExpression ) {
        valueEntity.setValue( orExpression.getStringExpression() );


    }

    @Override
    public void visit( Between between ) {
        valueEntity.setValue( between.toString() );


    }

    @Override
    public void visit( EqualsTo equalsTo ) {
        valueEntity.setValue( equalsTo.getStringExpression() );

    }

    @Override
    public void visit( GreaterThan greaterThan ) {
        valueEntity.setValue( greaterThan.getStringExpression() );

    }

    @Override
    public void visit( GreaterThanEquals greaterThanEquals ) {
        valueEntity.setValue( greaterThanEquals.getStringExpression() );

    }

    @Override
    public void visit( InExpression inExpression ) {
        valueEntity.setValue( inExpression.toString() );

    }

    @Override
    public void visit( IsNullExpression isNullExpression ) {
        valueEntity.setValue( isNullExpression.toString() );

    }

    @Override
    public void visit( LikeExpression likeExpression ) {
        valueEntity.setValue( likeExpression.getStringExpression() );

    }

    @Override
    public void visit( MinorThan minorThan ) {
        valueEntity.setValue( minorThan.getStringExpression() );

    }

    @Override
    public void visit( MinorThanEquals minorThanEquals ) {
        valueEntity.setValue( minorThanEquals.getStringExpression() );

    }

    @Override
    public void visit( NotEqualsTo notEqualsTo ) {
        valueEntity.setValue( notEqualsTo.getStringExpression() );

    }

    @Override
    public void visit( Column tableColumn ) {
        pl.piotrsukiennik.tuner.model.schema.Column col = queryContext.getColumn( tableColumn.getTable().getWholeTableName(), tableColumn.getColumnName() );
        Table table = col.getTable();
        Schema schema = table.getSchema();
        Database database = schema.getDatabase();
        valueEntity.setValue( database.getValue() + "." + schema.getValue() + "." + table.getValue() + "." + col.getValue() );
    }

    @Override
    public void visit( SubSelect subSelect ) {
        valueEntity.setValue( subSelect.toString() );

    }

    @Override
    public void visit( CaseExpression caseExpression ) {
        valueEntity.setValue( caseExpression.toString() );

    }

    @Override
    public void visit( WhenClause whenClause ) {
        valueEntity.setValue( whenClause.toString() );

    }

    @Override
    public void visit( ExistsExpression existsExpression ) {
        valueEntity.setValue( existsExpression.getStringExpression() );

    }

    @Override
    public void visit( AllComparisonExpression allComparisonExpression ) {
        valueEntity.setValue( allComparisonExpression.getSubSelect().toString() );

    }

    @Override
    public void visit( AnyComparisonExpression anyComparisonExpression ) {
        valueEntity.setValue( anyComparisonExpression.getSubSelect().toString() );

    }

    @Override
    public void visit( Concat concat ) {
        valueEntity.setValue( concat.getStringExpression() );

    }

    @Override
    public void visit( Matches matches ) {
        valueEntity.setValue( matches.getStringExpression() );

    }

    @Override
    public void visit( BitwiseAnd bitwiseAnd ) {
        valueEntity.setValue( bitwiseAnd.getStringExpression() );

    }

    @Override
    public void visit( BitwiseOr bitwiseOr ) {
        valueEntity.setValue( bitwiseOr.getStringExpression() );

    }

    @Override
    public void visit( BitwiseXor bitwiseXor ) {
        valueEntity.setValue( bitwiseXor.getStringExpression() );

    }

    @Override
    public void visit( JdbcNamedParameter jdbcNamedParameter ) {
        valueEntity.setValue( jdbcNamedParameter.getName() );

    }

    @Override
    public void visit( CastExpression cast ) {
        valueEntity.setValue( cast.getType().toString() );

    }

    @Override
    public void visit( Modulo modulo ) {
        valueEntity.setValue( modulo.getStringExpression() );

    }

    @Override
    public void visit( AnalyticExpression aexpr ) {
        valueEntity.setValue( aexpr.getName() );

    }

    @Override
    public void visit( ExtractExpression eexpr ) {
        valueEntity.setValue( eexpr.getName() );

    }

    @Override
    public void visit( IntervalExpression iexpr ) {
        valueEntity.setValue( iexpr.getParameter() );

    }

    @Override
    public void visit( OracleHierarchicalExpression oexpr ) {

    }

    @Override
    public void visit( RegExpMatchOperator rexpr ) {
        valueEntity.setValue( rexpr.getStringExpression() );

    }
}
