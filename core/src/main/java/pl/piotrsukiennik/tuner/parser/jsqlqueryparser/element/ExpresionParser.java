package pl.piotrsukiennik.tuner.parser.jsqlqueryparser.element;

import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.expression.operators.arithmetic.*;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.*;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.SubSelect;

/**
 * Author: Piotr Sukiennik
 * Date: 27.07.13
 * Time: 13:25
 */
public class ExpresionParser implements ExpressionVisitor {
    @Override
    public void visit(NullValue nullValue) {

    }

    @Override
    public void visit(Function function) {
    }

    @Override
    public void visit(InverseExpression inverseExpression) {

    }

    @Override
    public void visit(JdbcParameter jdbcParameter) {

    }

    @Override
    public void visit(DoubleValue doubleValue) {
    }

    @Override
    public void visit(LongValue longValue) {
    }

    @Override
    public void visit(DateValue dateValue) {
    }

    @Override
    public void visit(TimeValue timeValue) {
    }

    @Override
    public void visit(TimestampValue timestampValue) {
    }

    @Override
    public void visit(Parenthesis parenthesis) {
    }

    @Override
    public void visit(StringValue stringValue) {
    }

    @Override
    public void visit(Addition addition) {
    }

    @Override
    public void visit(Division division) {
    }

    @Override
    public void visit(Multiplication multiplication) {
    }

    @Override
    public void visit(Subtraction subtraction) {
    }

    @Override
    public void visit(AndExpression andExpression) {
    }

    @Override
    public void visit(OrExpression orExpression) {
    }

    @Override
    public void visit(Between between) {
    }

    @Override
    public void visit(EqualsTo equalsTo) {
    }

    @Override
    public void visit(GreaterThan greaterThan) {
    }

    @Override
    public void visit(GreaterThanEquals greaterThanEquals) {

    }

    @Override
    public void visit(InExpression inExpression) {
    }

    @Override
    public void visit(IsNullExpression isNullExpression) {
    }

    @Override
    public void visit(LikeExpression likeExpression) {
    }

    @Override
    public void visit(MinorThan minorThan) {
    }

    @Override
    public void visit(MinorThanEquals minorThanEquals) {
    }

    @Override
    public void visit(NotEqualsTo notEqualsTo) {
    }

    @Override
    public void visit(Column tableColumn) {
    }

    @Override
    public void visit(SubSelect subSelect) {
    }

    @Override
    public void visit(CaseExpression caseExpression) {
    }

    @Override
    public void visit(WhenClause whenClause) {
    }

    @Override
    public void visit(ExistsExpression existsExpression) {
    }

    @Override
    public void visit(AllComparisonExpression allComparisonExpression) {
    }

    @Override
    public void visit(AnyComparisonExpression anyComparisonExpression) {
    }

    @Override
    public void visit(Concat concat) {
    }

    @Override
    public void visit(Matches matches) {
    }

    @Override
    public void visit(BitwiseAnd bitwiseAnd) {
    }

    @Override
    public void visit(BitwiseOr bitwiseOr) {
    }

    @Override
    public void visit(BitwiseXor bitwiseXor) {
    }
}
