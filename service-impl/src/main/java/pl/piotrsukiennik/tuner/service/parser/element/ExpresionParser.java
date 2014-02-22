package pl.piotrsukiennik.tuner.service.parser.element;

import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.expression.operators.arithmetic.*;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.*;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.SubSelect;
import pl.piotrsukiennik.tuner.model.expression.*;
import pl.piotrsukiennik.tuner.model.expression.Expression;
import pl.piotrsukiennik.tuner.model.query.impl.SelectQuery;
import pl.piotrsukiennik.tuner.model.source.SubQuerySource;
import pl.piotrsukiennik.tuner.persistance.Dao;
import pl.piotrsukiennik.tuner.service.parser.QueryParsingContext;
import pl.piotrsukiennik.tuner.service.parser.statement.Visitor;

import java.util.LinkedHashSet;
import java.util.List;

/**
 * Author: Piotr Sukiennik
 * Date: 27.07.13
 * Time: 13:25
 */
public class ExpresionParser extends Visitor implements ExpressionVisitor {

    private Expression expression;


    public ExpresionParser( QueryParsingContext parsingContext ) {
        super( parsingContext );
    }

    public Expression getExpression() {
        if ( expression.getId() == 0 ) {
            Dao.getCommon().create( expression );
        }
        return expression;
    }

    protected void process( ExpressionOperator operator, BinaryExpression binaryExpression ) {
        PairExpression pairCondition = new PairExpression();
        pairCondition.setOperator( operator );
        ExpresionParser leftExpresionParser = new ExpresionParser( parsingContext );
        ExpresionParser rightExpresionParser = new ExpresionParser( parsingContext );
        binaryExpression.getLeftExpression().accept( leftExpresionParser );
        binaryExpression.getRightExpression().accept( rightExpresionParser );
        pairCondition.setLeftExpression( leftExpresionParser.getExpression() );
        pairCondition.setRightExpression( rightExpresionParser.getExpression() );
        this.expression = pairCondition;
    }

    @Override
    public void visit( JdbcNamedParameter jdbcNamedParameter ) {
        expression = new OperatorExpression();
        ( (OperatorExpression) expression ).setOperator( ExpressionOperator.JDBC_NAMED_PARAMETER );
        expression.setValue( jdbcNamedParameter.toString() );
    }

    @Override
    public void visit( CastExpression cast ) {
        expression = new OperatorExpression();
        ( (OperatorExpression) expression ).setOperator( ExpressionOperator.CAST_EXPRESSION );
        expression.setValue( cast.toString() );
    }

    @Override
    public void visit( Modulo modulo ) {
        expression = new OperatorExpression();
        ( (OperatorExpression) expression ).setOperator( ExpressionOperator.MODULO );
        expression.setValue( modulo.toString() );
    }

    @Override
    public void visit( AnalyticExpression aexpr ) {
        expression = new OperatorExpression();
        ( (OperatorExpression) expression ).setOperator( ExpressionOperator.ANALYTIC_EXPRESSION );
        expression.setValue( aexpr.toString() );
    }

    @Override
    public void visit( ExtractExpression eexpr ) {
        expression = new OperatorExpression();
        ( (OperatorExpression) expression ).setOperator( ExpressionOperator.EXTRACT_EXPRESSION );
        expression.setValue( eexpr.toString() );
    }

    @Override
    public void visit( IntervalExpression iexpr ) {
        expression = new OperatorExpression();
        ( (OperatorExpression) expression ).setOperator( ExpressionOperator.INTERVAL_EXPRESSION );
        expression.setValue( iexpr.toString() );
    }

    @Override
    public void visit( OracleHierarchicalExpression oexpr ) {
        expression = new OperatorExpression();
        ( (OperatorExpression) expression ).setOperator( ExpressionOperator.ORACLE_HIERARCHICAL_EXPRESSION );
        expression.setValue( oexpr.toString() );
    }

    @Override
    public void visit( RegExpMatchOperator rexpr ) {
        expression = new OperatorExpression();
        ( (OperatorExpression) expression ).setOperator( ExpressionOperator.REGEXP );
        expression.setValue( rexpr.toString() );
    }

    @Override
    public void visit( NullValue nullValue ) {
        expression = new OperatorExpression();
        ( (OperatorExpression) expression ).setOperator( ExpressionOperator.NULL_VALUE );
    }

    @Override
    public void visit( Function function ) {
        expression = new OperatorExpression();
        ( (OperatorExpression) expression ).setOperator( ExpressionOperator.FUNCTION );
        expression.setValue( function.toString() );
    }

    @Override
    public void visit( InverseExpression inverseExpression ) {
        expression = new OperatorExpression();
        inverseExpression.getExpression().accept( this );
        ( (OperatorExpression) expression ).setInverse( true );
    }

    @Override
    public void visit( JdbcParameter jdbcParameter ) {
        expression = new OperatorExpression();
        ( (OperatorExpression) expression ).setOperator( ExpressionOperator.JDBC_PARAMETER );
        expression.setValue( jdbcParameter.toString() );

    }

    @Override
    public void visit( DoubleValue doubleValue ) {
        expression = new OperatorExpression();
        ( (OperatorExpression) expression ).setOperator( ExpressionOperator.DOUBLE_VALUE );
        expression.setValue( Double.toString( doubleValue.getValue() ) );

    }

    @Override
    public void visit( LongValue longValue ) {
        expression = new OperatorExpression();
        ( (OperatorExpression) expression ).setOperator( ExpressionOperator.LONG_VALUE );
        expression.setValue( Long.toString( longValue.getValue() ) );

    }

    @Override
    public void visit( DateValue dateValue ) {
        expression = new OperatorExpression();
        ( (OperatorExpression) expression ).setOperator( ExpressionOperator.DATE_VALUE );
        expression.setValue( dateValue.getValue().toString() );

    }

    @Override
    public void visit( TimeValue timeValue ) {
        expression = new OperatorExpression();
        ( (OperatorExpression) expression ).setOperator( ExpressionOperator.TIME_VALUE );
        expression.setValue( timeValue.getValue().toString() );

    }

    @Override
    public void visit( TimestampValue timestampValue ) {
        expression = new OperatorExpression();
        ( (OperatorExpression) expression ).setOperator( ExpressionOperator.TIMESTAMP_VALUE );
        expression.setValue( timestampValue.getValue().toString() );

    }

    @Override
    public void visit( Parenthesis parenthesis ) {
        SubExpression subCondition = new SubExpression();
        subCondition.setOperator( ExpressionOperator.PARENTHESIS );

        ExpresionParser expresionParser = new ExpresionParser( parsingContext );
        parenthesis.getExpression().accept( expresionParser );
        subCondition.setSubExpression( (OperatorExpression) expresionParser.getExpression() );
        expression = subCondition;
    }

    @Override
    public void visit( StringValue stringValue ) {
        expression = new OperatorExpression();
        ( (OperatorExpression) expression ).setOperator( ExpressionOperator.STRING_VALUE );
        expression.setValue( stringValue.getValue() );

    }

    @Override
    public void visit( Addition addition ) {
        process( ExpressionOperator.ADDITION, addition );
    }

    @Override
    public void visit( Division division ) {
        process( ExpressionOperator.DIVISION, division );
    }

    @Override
    public void visit( Multiplication multiplication ) {
        process( ExpressionOperator.MULTIPLICATION, multiplication );
    }

    @Override
    public void visit( Subtraction subtraction ) {
        process( ExpressionOperator.SUBTRACTION, subtraction );
    }

    @Override
    public void visit( AndExpression andExpression ) {
        process( ExpressionOperator.AND, andExpression );
    }

    @Override
    public void visit( OrExpression orExpression ) {
        process( ExpressionOperator.OR, orExpression );
    }

    @Override
    public void visit( Between between ) {

        BetweenExpression betweenCondition = new BetweenExpression();
        ExpresionParser leftExpresionParser = new ExpresionParser( parsingContext );
        ExpresionParser startExpresionParser = new ExpresionParser( parsingContext );
        ExpresionParser endExpresionParser = new ExpresionParser( parsingContext );
        between.getLeftExpression().accept( leftExpresionParser );
        between.getBetweenExpressionStart().accept( startExpresionParser );
        between.getBetweenExpressionEnd().accept( endExpresionParser );
        betweenCondition.setLeftExpression( leftExpresionParser.getExpression() );
        betweenCondition.setStartExpression( startExpresionParser.getExpression() );
        betweenCondition.setEndExpression( endExpresionParser.getExpression() );
        expression = betweenCondition;

    }

    @Override
    public void visit( EqualsTo equalsTo ) {
        process( ExpressionOperator.EQUALS_TO, equalsTo );
    }

    @Override
    public void visit( GreaterThan greaterThan ) {
        process( ExpressionOperator.GREATER_THAN, greaterThan );
    }

    @Override
    public void visit( GreaterThanEquals greaterThanEquals ) {
        process( ExpressionOperator.GREATER_THAN_EQUALS, greaterThanEquals );
    }

    @Override
    public void visit( InExpression inExpression ) {
        ExpresionParser expresionParser = new ExpresionParser( parsingContext );
        inExpression.getLeftExpression().accept( expresionParser );

        SubExpression subCondition = new SubExpression();
        subCondition.setOperator( ExpressionOperator.IN );
        subCondition.setSubExpression( expresionParser.getExpression() );
        subCondition.setValue( inExpression.getRightItemsList().toString() );
        expression = subCondition;
    }

    @Override
    public void visit( IsNullExpression isNullExpression ) {
        ExpresionParser expresionParser = new ExpresionParser( parsingContext );
        isNullExpression.getLeftExpression().accept( expresionParser );
        SubExpression subCondition = new SubExpression();
        subCondition.setSubExpression( expresionParser.getExpression() );
        subCondition.setOperator( ExpressionOperator.IS_NULL );
        expression = subCondition;
    }

    @Override
    public void visit( LikeExpression likeExpression ) {
        process( ExpressionOperator.LIKE, likeExpression );
    }

    @Override
    public void visit( MinorThan minorThan ) {
        process( ExpressionOperator.MINOR_THAN, minorThan );
    }

    @Override
    public void visit( MinorThanEquals minorThanEquals ) {
        process( ExpressionOperator.MINOR_THAN_EQUALS, minorThanEquals );
    }

    @Override
    public void visit( NotEqualsTo notEqualsTo ) {
        process( ExpressionOperator.NOT_EQUALS_TO, notEqualsTo );
    }

    @Override
    public void visit( Column tableColumn ) {

        expression = parsingContext.getColumnProjection( tableColumn );
    }

    @Override
    public void visit( SubSelect subSelect ) {
        SubQuerySource subQuerySource = new SubQuerySource();
        if ( subSelect.getAlias() != null ) {
            subQuerySource.setAlias( subSelect.getAlias().getName() );
        }
        subQuerySource.setValue( subSelect.getSelectBody().toString() );

        SelectQuery selectQuery = new SelectQuery();
        SelectBodyParser<SelectQuery> selectBodyParser = new SelectBodyParser<SelectQuery>( parsingContext, selectQuery );
        subSelect.getSelectBody().accept( selectBodyParser );
        subQuerySource.setSelectQuery( selectQuery );

        SubSelectExpression subSelectCondition = new SubSelectExpression();
        subSelectCondition.setSubQuery( selectQuery );

        expression = subSelectCondition;
    }

    @Override
    public void visit( CaseExpression caseExpression ) {

        ListExpression listCondition = new ListExpression();
        listCondition.setOperator( ExpressionOperator.CASE );
        listCondition.setSubExpressions( new LinkedHashSet<Expression>() );

        ExpresionParser expresionParser = new ExpresionParser( parsingContext );

        caseExpression.getSwitchExpression().accept( expresionParser );

        listCondition.getSubExpressions().add( expresionParser.getExpression() );

        List<net.sf.jsqlparser.expression.Expression> whenClauses = caseExpression.getWhenClauses();
        for ( net.sf.jsqlparser.expression.Expression expression : whenClauses ) {
            expression.accept( expresionParser );
            listCondition.getSubExpressions().add( expresionParser.getExpression() );
        }

        caseExpression.getElseExpression().accept( expresionParser );
        listCondition.getSubExpressions().add( expresionParser.getExpression() );

        expression = listCondition;
    }

    @Override
    public void visit( WhenClause whenClause ) {
        PairExpression pairCondition = new PairExpression();

        ExpresionParser expresionParser = new ExpresionParser( parsingContext );

        whenClause.getWhenExpression().accept( expresionParser );

        pairCondition.setLeftExpression( expresionParser.getExpression() );

        whenClause.getThenExpression().accept( expresionParser );
        pairCondition.setRightExpression( expresionParser.getExpression() );

        pairCondition.setOperator( ExpressionOperator.WHEN_CLAUSE );

        expression = pairCondition;
    }

    @Override
    public void visit( ExistsExpression existsExpression ) {
        SubExpression subCondition = new SubExpression();
        subCondition.setOperator( ExpressionOperator.EXISTS );

        ExpresionParser expresionParser = new ExpresionParser( parsingContext );
        existsExpression.getRightExpression().accept( expresionParser );

        subCondition.setSubExpression( expresionParser.getExpression() );

        expression = subCondition;

    }

    @Override
    public void visit( AllComparisonExpression allComparisonExpression ) {
        SubSelect subSelect = allComparisonExpression.getSubSelect();
        visit( subSelect );
        ( (OperatorExpression) expression ).setOperator( ExpressionOperator.ALL_COMPARISON );
    }

    @Override
    public void visit( AnyComparisonExpression anyComparisonExpression ) {
        SubSelect subSelect = anyComparisonExpression.getSubSelect();
        visit( subSelect );
        ( (OperatorExpression) expression ).setOperator( ExpressionOperator.ANY_COMPARISON );

    }

    @Override
    public void visit( Concat concat ) {
        process( ExpressionOperator.CONCAT, concat );
    }

    @Override
    public void visit( Matches matches ) {
        process( ExpressionOperator.MATCHES, matches );
    }

    @Override
    public void visit( BitwiseAnd bitwiseAnd ) {
        process( ExpressionOperator.BITWISE_AND, bitwiseAnd );
    }

    @Override
    public void visit( BitwiseOr bitwiseOr ) {
        process( ExpressionOperator.BITWISE_OR, bitwiseOr );
    }

    @Override
    public void visit( BitwiseXor bitwiseXor ) {
        process( ExpressionOperator.BITWISE_XOR, bitwiseXor );
    }
}
