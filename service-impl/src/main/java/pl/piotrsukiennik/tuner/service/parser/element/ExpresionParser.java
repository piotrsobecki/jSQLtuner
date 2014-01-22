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
import pl.piotrsukiennik.tuner.persistance.DaoHolder;
import pl.piotrsukiennik.tuner.service.QueryElementParserService;
import pl.piotrsukiennik.tuner.service.parser.statement.Visitor;
import pl.piotrsukiennik.tuner.service.query.QueryContext;

import java.util.LinkedHashSet;
import java.util.List;

/**
 * Author: Piotr Sukiennik
 * Date: 27.07.13
 * Time: 13:25
 */
public class ExpresionParser extends Visitor implements ExpressionVisitor {

    private Condition condition;


    public ExpresionParser( QueryElementParserService queryElementParserService, QueryContext queryContext ) {
        super( queryElementParserService, queryContext );
    }

    public Condition getCondition() {
        if ( condition.getId() == 0 ) {
            DaoHolder.getCommonDao().create( condition );
        }
        return condition;
    }

    protected void process( ConditionOperator operator, BinaryExpression binaryExpression ) {
        PairCondition pairCondition = new PairCondition();
        pairCondition.setOperator( operator );
        ExpresionParser leftExpresionParser = new ExpresionParser( queryElementParserService, queryContext );
        ExpresionParser rightExpresionParser = new ExpresionParser( queryElementParserService, queryContext );
        binaryExpression.getLeftExpression().accept( leftExpresionParser );
        binaryExpression.getRightExpression().accept( rightExpresionParser );
        pairCondition.setLeftCondition( leftExpresionParser.getCondition() );
        pairCondition.setRightCondition( rightExpresionParser.getCondition() );
        this.condition = pairCondition;
    }

    @Override
    public void visit( NullValue nullValue ) {
        condition = new Condition();
        condition.setOperator( ConditionOperator.NullValue );

    }

    @Override
    public void visit( Function function ) {
        condition = new Condition();
        condition.setOperator( ConditionOperator.Function );
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
        condition.setOperator( ConditionOperator.JdbcParameter );
        condition.setValue( jdbcParameter.toString() );

    }

    @Override
    public void visit( DoubleValue doubleValue ) {
        condition = new Condition();
        condition.setOperator( ConditionOperator.DoubleValue );
        condition.setValue( Double.toString( doubleValue.getValue() ) );

    }

    @Override
    public void visit( LongValue longValue ) {
        condition = new Condition();
        condition.setOperator( ConditionOperator.LongValue );
        condition.setValue( Long.toString( longValue.getValue() ) );

    }

    @Override
    public void visit( DateValue dateValue ) {
        condition = new Condition();
        condition.setOperator( ConditionOperator.LongValue );
        condition.setValue( dateValue.getValue().toString() );

    }

    @Override
    public void visit( TimeValue timeValue ) {
        condition = new Condition();
        condition.setOperator( ConditionOperator.LongValue );
        condition.setValue( timeValue.getValue().toString() );

    }

    @Override
    public void visit( TimestampValue timestampValue ) {
        condition = new Condition();
        condition.setOperator( ConditionOperator.LongValue );
        condition.setValue( timestampValue.getValue().toString() );

    }

    @Override
    public void visit( Parenthesis parenthesis ) {
        SubCondition subCondition = new SubCondition();
        subCondition.setOperator( ConditionOperator.Parenthesis );

        ExpresionParser expresionParser = new ExpresionParser( queryElementParserService, queryContext );
        parenthesis.getExpression().accept( expresionParser );
        subCondition.setSubCondition( expresionParser.getCondition() );

        condition = subCondition;
    }

    @Override
    public void visit( StringValue stringValue ) {
        condition = new Condition();
        condition.setOperator( ConditionOperator.LongValue );
        condition.setValue( stringValue.getValue() );

    }

    @Override
    public void visit( Addition addition ) {
        process( ConditionOperator.Addition, addition );
    }

    @Override
    public void visit( Division division ) {
        process( ConditionOperator.Division, division );
    }

    @Override
    public void visit( Multiplication multiplication ) {
        process( ConditionOperator.Multiplication, multiplication );
    }

    @Override
    public void visit( Subtraction subtraction ) {
        process( ConditionOperator.Subtraction, subtraction );
    }

    @Override
    public void visit( AndExpression andExpression ) {
        process( ConditionOperator.And, andExpression );
    }

    @Override
    public void visit( OrExpression orExpression ) {
        process( ConditionOperator.Or, orExpression );
    }

    @Override
    public void visit( Between between ) {

        BetweenCondition betweenCondition = new BetweenCondition();
        ExpresionParser leftExpresionParser = new ExpresionParser( queryElementParserService, queryContext );
        ExpresionParser startExpresionParser = new ExpresionParser( queryElementParserService, queryContext );
        ExpresionParser endExpresionParser = new ExpresionParser( queryElementParserService, queryContext );
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
        process( ConditionOperator.EqualsTo, equalsTo );
    }

    @Override
    public void visit( GreaterThan greaterThan ) {
        process( ConditionOperator.GreaterThan, greaterThan );
    }

    @Override
    public void visit( GreaterThanEquals greaterThanEquals ) {
        process( ConditionOperator.GreaterThanEquals, greaterThanEquals );
    }

    @Override
    public void visit( InExpression inExpression ) {
        ExpresionParser expresionParser = new ExpresionParser( queryElementParserService, queryContext );
        inExpression.getLeftExpression().accept( expresionParser );

        SubCondition subCondition = new SubCondition();
        subCondition.setOperator( ConditionOperator.In );
        subCondition.setSubCondition( expresionParser.getCondition() );
        subCondition.setValue( inExpression.getItemsList().toString() );
        condition = subCondition;
    }

    @Override
    public void visit( IsNullExpression isNullExpression ) {
        ExpresionParser expresionParser = new ExpresionParser( queryElementParserService, queryContext );
        isNullExpression.getLeftExpression().accept( expresionParser );
        SubCondition subCondition = new SubCondition();
        subCondition.setSubCondition( expresionParser.getCondition() );
        subCondition.setOperator( ConditionOperator.IsNull );
        condition = subCondition;
    }

    @Override
    public void visit( LikeExpression likeExpression ) {
        process( ConditionOperator.Like, likeExpression );
    }

    @Override
    public void visit( MinorThan minorThan ) {
        process( ConditionOperator.MinorThan, minorThan );
    }

    @Override
    public void visit( MinorThanEquals minorThanEquals ) {
        process( ConditionOperator.MinorThanEquals, minorThanEquals );
    }

    @Override
    public void visit( NotEqualsTo notEqualsTo ) {
        process( ConditionOperator.NotEqualsTo, notEqualsTo );
    }

    @Override
    public void visit( Column tableColumn ) {
        ProjectionValueCondition projectionValueCondition = new ProjectionValueCondition();
        projectionValueCondition.setProjection( queryElementParserService.getColumnProjection( queryContext, tableColumn ) );
        condition = projectionValueCondition;
    }

    @Override
    public void visit( SubSelect subSelect ) {
        SubQuerySource subQuerySource = new SubQuerySource();
        subQuerySource.setAlias( subSelect.getAlias() );
        subQuerySource.setValue( subSelect.getSelectBody().toString() );

        SelectQuery selectQuery = new SelectQuery();
        SelectBodyParser<SelectQuery> selectBodyParser = new SelectBodyParser<SelectQuery>( queryElementParserService, queryContext, selectQuery );
        subSelect.getSelectBody().accept( selectBodyParser );
        subQuerySource.setSelectQuery( selectQuery );

        SubSelectCondition subSelectCondition = new SubSelectCondition();
        subSelectCondition.setSubQuery( selectQuery );

        condition = subSelectCondition;
    }

    @Override
    public void visit( CaseExpression caseExpression ) {

        ListCondition listCondition = new ListCondition();
        listCondition.setOperator( ConditionOperator.Case );
        listCondition.setSubConditions( new LinkedHashSet<Condition>() );

        ExpresionParser expresionParser = new ExpresionParser( queryElementParserService, queryContext );

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

        ExpresionParser expresionParser = new ExpresionParser( queryElementParserService, queryContext );

        whenClause.getWhenExpression().accept( expresionParser );

        pairCondition.setLeftCondition( expresionParser.getCondition() );

        whenClause.getThenExpression().accept( expresionParser );
        pairCondition.setRightCondition( expresionParser.getCondition() );

        pairCondition.setOperator( ConditionOperator.WhenClause );

        condition = pairCondition;
    }

    @Override
    public void visit( ExistsExpression existsExpression ) {
        SubCondition subCondition = new SubCondition();
        subCondition.setOperator( ConditionOperator.Exists );

        ExpresionParser expresionParser = new ExpresionParser( queryElementParserService, queryContext );
        existsExpression.getRightExpression().accept( expresionParser );

        subCondition.setSubCondition( expresionParser.getCondition() );

        condition = subCondition;

    }

    @Override
    public void visit( AllComparisonExpression allComparisonExpression ) {
        SubSelect subSelect = allComparisonExpression.GetSubSelect();
        visit( subSelect );
        condition.setOperator( ConditionOperator.AllComparison );
    }

    @Override
    public void visit( AnyComparisonExpression anyComparisonExpression ) {
        SubSelect subSelect = anyComparisonExpression.GetSubSelect();
        visit( subSelect );
        condition.setOperator( ConditionOperator.AnyComparison );

    }

    @Override
    public void visit( Concat concat ) {
        process( ConditionOperator.Concat, concat );
    }

    @Override
    public void visit( Matches matches ) {
        process( ConditionOperator.Matches, matches );
    }

    @Override
    public void visit( BitwiseAnd bitwiseAnd ) {
        process( ConditionOperator.BitwiseAnd, bitwiseAnd );
    }

    @Override
    public void visit( BitwiseOr bitwiseOr ) {
        process( ConditionOperator.BitwiseOr, bitwiseOr );
    }

    @Override
    public void visit( BitwiseXor bitwiseXor ) {
        process( ConditionOperator.BitwiseXor, bitwiseXor );
    }
}
