package pl.piotrsukiennik.tuner.parser.jsqlqueryparser.element;

import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.expression.operators.arithmetic.*;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.*;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.SubSelect;
import pl.piotrsukiennik.tuner.persistance.model.query.Query;
import pl.piotrsukiennik.tuner.persistance.model.query.SelectQuery;
import pl.piotrsukiennik.tuner.persistance.model.query.SourcesAware;
import pl.piotrsukiennik.tuner.persistance.model.query.source.SubQuerySource;
import pl.piotrsukiennik.tuner.query.QueryContextManager;
import pl.piotrsukiennik.tuner.util.QueryUtils;

/**
 * Author: Piotr Sukiennik
 * Date: 27.07.13
 * Time: 13:25
 */
public class ExpresionParser implements ExpressionVisitor {

    private Query sourceQuery;
    private QueryContextManager queryContextManager;

    public ExpresionParser(QueryContextManager queryContextManager, Query sourceQuery) {
        this.sourceQuery=sourceQuery;
        this.queryContextManager=queryContextManager;
    }


    protected void process(BinaryExpression binaryExpression){
        ExpresionParser leftExpresionParser = new ExpresionParser(queryContextManager,sourceQuery);
        ExpresionParser rightExpresionParser = new ExpresionParser(queryContextManager,sourceQuery);
        binaryExpression.getLeftExpression().accept(leftExpresionParser);
        binaryExpression.getRightExpression().accept(rightExpresionParser);

    }

    @Override
    public void visit(NullValue nullValue) {

    }

    @Override
    public void visit(Function function) {

    }

    @Override
    public void visit(InverseExpression inverseExpression) {
        inverseExpression.getExpression().accept(this);
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
        process(addition);
    }

    @Override
    public void visit(Division division) {
       process(division);
    }

    @Override
    public void visit(Multiplication multiplication) {
        process(multiplication);
    }

    @Override
    public void visit(Subtraction subtraction) {
        process(subtraction);
    }

    @Override
    public void visit(AndExpression andExpression) {
          process(andExpression);
    }

    @Override
    public void visit(OrExpression orExpression) {
        process(orExpression);
    }

    @Override
    public void visit(Between between) {

        ExpresionParser leftExpresionParser = new ExpresionParser(queryContextManager,sourceQuery);
        ExpresionParser startExpresionParser = new ExpresionParser(queryContextManager,sourceQuery);
        ExpresionParser endExpresionParser = new ExpresionParser(queryContextManager,sourceQuery);
        between.getLeftExpression().accept(leftExpresionParser);
        between.getBetweenExpressionStart().accept(startExpresionParser);
        between.getBetweenExpressionEnd().accept(endExpresionParser);

    }

    @Override
    public void visit(EqualsTo equalsTo) {
        process(equalsTo);
    }

    @Override
    public void visit(GreaterThan greaterThan) {
        process(greaterThan);
    }

    @Override
    public void visit(GreaterThanEquals greaterThanEquals) {
        process(greaterThanEquals);
    }

    @Override
    public void visit(InExpression inExpression) {
        ExpresionParser expresionParser = new ExpresionParser(queryContextManager,sourceQuery);
        inExpression.getLeftExpression().accept(expresionParser);
        ItemsListParser itemsListParser = new ItemsListParser(queryContextManager,sourceQuery);
        inExpression.getItemsList().accept(itemsListParser);
    }

    @Override
    public void visit(IsNullExpression isNullExpression) {
        ExpresionParser expresionParser = new ExpresionParser(queryContextManager,sourceQuery);
        isNullExpression.getLeftExpression().accept(expresionParser);
    }

    @Override
    public void visit(LikeExpression likeExpression) {
        process(likeExpression);
    }

    @Override
    public void visit(MinorThan minorThan) {
        process(minorThan);
    }

    @Override
    public void visit(MinorThanEquals minorThanEquals) {
        process(minorThanEquals);
    }

    @Override
    public void visit(NotEqualsTo notEqualsTo) {
        process(notEqualsTo);
    }

    @Override
    public void visit(Column tableColumn) {
        queryContextManager.getColumn(tableColumn.getTable().getWholeTableName(),tableColumn.getColumnName());
    }

    @Override
    public void visit(SubSelect subSelect) {
        SubQuerySource subQuerySource = new SubQuerySource();
        subQuerySource.setAlias(subSelect.getAlias());
        subQuerySource.setValue(subSelect.getSelectBody().toString());

        SelectQuery selectQuery = new SelectQuery();
        selectQuery.setParentQuery(sourceQuery);
        SelectBodyParser<SelectQuery> selectBodyParser = new SelectBodyParser<SelectQuery>(queryContextManager,selectQuery);
        subSelect.getSelectBody().accept(selectBodyParser);
        subQuerySource.setSelectQuery(selectQuery);
        if (sourceQuery instanceof SourcesAware){
            QueryUtils.addSource((SourcesAware) sourceQuery, subQuerySource);
        }
    }

    @Override
    public void visit(CaseExpression caseExpression) {

    }

    @Override
    public void visit(WhenClause whenClause) {

    }

    @Override
    public void visit(ExistsExpression existsExpression) {
        ExpresionParser expresionParser = new ExpresionParser(queryContextManager,sourceQuery);
        existsExpression.getRightExpression().accept(expresionParser);
    }

    @Override
    public void visit(AllComparisonExpression allComparisonExpression) {
        SubSelect subSelect = allComparisonExpression.GetSubSelect();
        visit(subSelect);
    }

    @Override
    public void visit(AnyComparisonExpression anyComparisonExpression) {
        SubSelect subSelect = anyComparisonExpression.GetSubSelect();
        visit(subSelect);
    }

    @Override
    public void visit(Concat concat) {

    }

    @Override
    public void visit(Matches matches) {
        process(matches);

    }

    @Override
    public void visit(BitwiseAnd bitwiseAnd) {
        process(bitwiseAnd);
    }

    @Override
    public void visit(BitwiseOr bitwiseOr) {
        process(bitwiseOr);
    }

    @Override
    public void visit(BitwiseXor bitwiseXor) {
        process(bitwiseXor);
    }
}
