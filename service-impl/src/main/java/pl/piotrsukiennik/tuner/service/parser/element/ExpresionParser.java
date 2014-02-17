package pl.piotrsukiennik.tuner.service.parser.element;

import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.expression.operators.arithmetic.*;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.*;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.SubSelect;
import pl.piotrsukiennik.tuner.model.query.SelectQuery;
import pl.piotrsukiennik.tuner.model.query.condition.*;
import pl.piotrsukiennik.tuner.model.query.source.SubQuerySource;
import pl.piotrsukiennik.tuner.persistance.Dao;
import pl.piotrsukiennik.tuner.service.QueryContext;
import pl.piotrsukiennik.tuner.service.parser.ElementParserService;
import pl.piotrsukiennik.tuner.service.parser.statement.Visitor;

import java.util.LinkedHashSet;
import java.util.List;

/**
 * Author: Piotr Sukiennik
 * Date: 27.07.13
 * Time: 13:25
 */
public class ExpresionParser extends Visitor implements ExpressionVisitor {

    private Condition condition;


    public ExpresionParser( ElementParserService elementParserService, QueryContext queryContext ) {
        super( elementParserService, queryContext );
    }

    public Condition getCondition() {
        if ( condition.getId() == 0 ) {
            Dao.getCommonDao().create( condition );
        }
        return condition;
    }

    protected void process( ConditionOperator operator, BinaryExpression binaryExpression ) {
        PairCondition pairCondition = new PairCondition();
        pairCondition.setOperator( operator );
        ExpresionParser leftExpresionParser = new ExpresionParser( elementParserService, queryContext );
        ExpresionParser rightExpresionParser = new ExpresionParser( elementParserService, queryContext );
        binaryExpression.getLeftExpression().accept( leftExpresionParser );
        binaryExpression.getRightExpression().accept( rightExpresionParser );
        pairCondition.setLeftCondition( leftExpresionParser.getCondition() );
        pairCondition.setRightCondition( rightExpresionParser.getCondition() );
        this.condition = pairCondition;
    }

    @Override
    public void visit( NullValue nullValue ) {
        condition = new Condition();
        condition.setOperator( ConditionOperator.NULL_VALUE );

    }

    @Override
    public void visit( Function function ) {
        condition = new Condition();
        condition.setOperator( ConditionOperator.FUNCTION );
        condition.setValue( function.toString() );
    }

    @Override
    public void visit( InverseExpression inverseExpression ) {
        condition = new Condition();
        inverseExpression.getExpression().accept( this );
        condition.setInverse( true );
    }

    @Override
    public void visit( JdbcParameter jdbcParameter ) {
        condition = new Condition();
        condition.setOperator( ConditionOperator.JDBC_PARAMETER );
        condition.setValue( jdbcParameter.toString() );

    }

    @Override
    public void visit( DoubleValue doubleValue ) {
        condition = new Condition();
        condition.setOperator( ConditionOperator.DOUBLE_VALUE );
        condition.setValue( Double.toString( doubleValue.getValue() ) );

    }

    @Override
    public void visit( LongValue longValue ) {
        condition = new Condition();
        condition.setOperator( ConditionOperator.LONG_VALUE );
        condition.setValue( Long.toString( longValue.getValue() ) );

    }

    @Override
    public void visit( DateValue dateValue ) {
        condition = new Condition();
        condition.setOperator( ConditionOperator.DATE_VALUE );
        condition.setValue( dateValue.getValue().toString() );

    }

    @Override
    public void visit( TimeValue timeValue ) {
        condition = new Condition();
        condition.setOperator( ConditionOperator.TIME_VALUE );
        condition.setValue( timeValue.getValue().toString() );

    }

    @Override
    public void visit( TimestampValue timestampValue ) {
        condition = new Condition();
        condition.setOperator( ConditionOperator.TIMESTAMP_VALUE );
        condition.setValue( timestampValue.getValue().toString() );

    }

    @Override
    public void visit( Parenthesis parenthesis ) {
        SubCondition subCondition = new SubCondition();
        subCondition.setOperator( ConditionOperator.PARENTHESIS );

        ExpresionParser expresionParser = new ExpresionParser( elementParserService, queryContext );
        parenthesis.getExpression().accept( expresionParser );
        subCondition.setSubCondition( expresionParser.getCondition() );

        condition = subCondition;
    }

    @Override
    public void visit( StringValue stringValue ) {
        condition = new Condition();
        condition.setOperator( ConditionOperator.STRING_VALUE );
        condition.setValue( stringValue.getValue() );

    }

    @Override
    public void visit( Addition addition ) {
        process( ConditionOperator.ADDITION, addition );
    }

    @Override
    public void visit( Division division ) {
        process( ConditionOperator.DIVISION, division );
    }

    @Override
    public void visit( Multiplication multiplication ) {
        process( ConditionOperator.MULTIPLICATION, multiplication );
    }

    @Override
    public void visit( Subtraction subtraction ) {
        process( ConditionOperator.SUBTRACTION, subtraction );
    }

    @Override
    public void visit( AndExpression andExpression ) {
        process( ConditionOperator.AND, andExpression );
    }

    @Override
    public void visit( OrExpression orExpression ) {
        process( ConditionOperator.OR, orExpression );
    }

    @Override
    public void visit( Between between ) {

        BetweenCondition betweenCondition = new BetweenCondition();
        ExpresionParser leftExpresionParser = new ExpresionParser( elementParserService, queryContext );
        ExpresionParser startExpresionParser = new ExpresionParser( elementParserService, queryContext );
        ExpresionParser endExpresionParser = new ExpresionParser( elementParserService, queryContext );
        between.getLeftExpression().accept( leftExpresionParser );
        between.getBetweenExpressionStart().accept( startExpresionParser );
        between.getBetweenExpressionEnd().accept( endExpresionParser );
        betweenCondition.setLeftCondition( leftExpresionParser.getCondition() );
        betweenCondition.setStartCondition( startExpresionParser.getCondition() );
        betweenCondition.setEndCondition( endExpresionParser.getCondition() );
        condition = betweenCondition;

    }

    @Override
    public void visit( EqualsTo equalsTo ) {
        process( ConditionOperator.EQUALS_TO, equalsTo );
    }

    @Override
    public void visit( GreaterThan greaterThan ) {
        process( ConditionOperator.GREATER_THAN, greaterThan );
    }

    @Override
    public void visit( GreaterThanEquals greaterThanEquals ) {
        process( ConditionOperator.GREATER_THAN_EQUALS, greaterThanEquals );
    }

    @Override
    public void visit( InExpression inExpression ) {
        ExpresionParser expresionParser = new ExpresionParser( elementParserService, queryContext );
        inExpression.getLeftExpression().accept( expresionParser );

        SubCondition subCondition = new SubCondition();
        subCondition.setOperator( ConditionOperator.IN );
        subCondition.setSubCondition( expresionParser.getCondition() );
        subCondition.setValue( inExpression.getItemsList().toString() );
        condition = subCondition;
    }

    @Override
    public void visit( IsNullExpression isNullExpression ) {
        ExpresionParser expresionParser = new ExpresionParser( elementParserService, queryContext );
        isNullExpression.getLeftExpression().accept( expresionParser );
        SubCondition subCondition = new SubCondition();
        subCondition.setSubCondition( expresionParser.getCondition() );
        subCondition.setOperator( ConditionOperator.IS_NULL );
        condition = subCondition;
    }

    @Override
    public void visit( LikeExpression likeExpression ) {
        process( ConditionOperator.LIKE, likeExpression );
    }

    @Override
    public void visit( MinorThan minorThan ) {
        process( ConditionOperator.MINOR_THAN, minorThan );
    }

    @Override
    public void visit( MinorThanEquals minorThanEquals ) {
        process( ConditionOperator.MINOR_THAN_EQUALS, minorThanEquals );
    }

    @Override
    public void visit( NotEqualsTo notEqualsTo ) {
        process( ConditionOperator.NOT_EQUALS_TO, notEqualsTo );
    }

    @Override
    public void visit( Column tableColumn ) {
        ProjectionValueCondition projectionValueCondition = new ProjectionValueCondition();
        projectionValueCondition.setProjection( elementParserService.getColumnProjection( queryContext, tableColumn ) );
        condition = projectionValueCondition;
    }

    @Override
    public void visit( SubSelect subSelect ) {
        SubQuerySource subQuerySource = new SubQuerySource();
        subQuerySource.setAlias( subSelect.getAlias() );
        subQuerySource.setValue( subSelect.getSelectBody().toString() );

        SelectQuery selectQuery = new SelectQuery();
        SelectBodyParser<SelectQuery> selectBodyParser = new SelectBodyParser<SelectQuery>( elementParserService, queryContext, selectQuery );
        subSelect.getSelectBody().accept( selectBodyParser );
        subQuerySource.setSelectQuery( selectQuery );

        SubSelectCondition subSelectCondition = new SubSelectCondition();
        subSelectCondition.setSubQuery( selectQuery );

        condition = subSelectCondition;
    }

    @Override
    public void visit( CaseExpression caseExpression ) {

        ListCondition listCondition = new ListCondition();
        listCondition.setOperator( ConditionOperator.CASE );
        listCondition.setSubConditions( new LinkedHashSet<Condition>() );

        ExpresionParser expresionParser = new ExpresionParser( elementParserService, queryContext );

        caseExpression.getSwitchExpression().accept( expresionParser );

        listCondition.getSubConditions().add( expresionParser.getCondition() );

        List<Expression> whenClauses = caseExpression.getWhenClauses();
        for ( Expression expression : whenClauses ) {
            expression.accept( expresionParser );
            listCondition.getSubConditions().add( expresionParser.getCondition() );
        }

        caseExpression.getElseExpression().accept( expresionParser );
        listCondition.getSubConditions().add( expresionParser.getCondition() );

        condition = listCondition;
    }

    @Override
    public void visit( WhenClause whenClause ) {
        PairCondition pairCondition = new PairCondition();

        ExpresionParser expresionParser = new ExpresionParser( elementParserService, queryContext );

        whenClause.getWhenExpression().accept( expresionParser );

        pairCondition.setLeftCondition( expresionParser.getCondition() );

        whenClause.getThenExpression().accept( expresionParser );
        pairCondition.setRightCondition( expresionParser.getCondition() );

        pairCondition.setOperator( ConditionOperator.WHEN_CLAUSE );

        condition = pairCondition;
    }

    @Override
    public void visit( ExistsExpression existsExpression ) {
        SubCondition subCondition = new SubCondition();
        subCondition.setOperator( ConditionOperator.EXISTS );

        ExpresionParser expresionParser = new ExpresionParser( elementParserService, queryContext );
        existsExpression.getRightExpression().accept( expresionParser );

        subCondition.setSubCondition( expresionParser.getCondition() );

        condition = subCondition;

    }

    @Override
    public void visit( AllComparisonExpression allComparisonExpression ) {
        SubSelect subSelect = allComparisonExpression.GetSubSelect();
        visit( subSelect );
        condition.setOperator( ConditionOperator.ALL_COMPARISON );
    }

    @Override
    public void visit( AnyComparisonExpression anyComparisonExpression ) {
        SubSelect subSelect = anyComparisonExpression.GetSubSelect();
        visit( subSelect );
        condition.setOperator( ConditionOperator.ANY_COMPARISON );

    }

    @Override
    public void visit( Concat concat ) {
        process( ConditionOperator.CONCAT, concat );
    }

    @Override
    public void visit( Matches matches ) {
        process( ConditionOperator.MATCHES, matches );
    }

    @Override
    public void visit( BitwiseAnd bitwiseAnd ) {
        process( ConditionOperator.BITWISE_AND, bitwiseAnd );
    }

    @Override
    public void visit( BitwiseOr bitwiseOr ) {
        process( ConditionOperator.BITWISE_OR, bitwiseOr );
    }

    @Override
    public void visit( BitwiseXor bitwiseXor ) {
        process( ConditionOperator.BITWISE_XOR, bitwiseXor );
    }
}
