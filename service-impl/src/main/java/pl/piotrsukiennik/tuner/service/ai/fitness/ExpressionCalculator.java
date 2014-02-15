package pl.piotrsukiennik.tuner.service.ai.fitness;

import de.congrace.exp4j.Calculable;
import de.congrace.exp4j.ExpressionBuilder;
import de.congrace.exp4j.UnknownFunctionException;
import de.congrace.exp4j.UnparsableExpressionException;

/**
 * @author Piotr Sukiennik
 * @date 13.02.14
 */
public abstract class ExpressionCalculator {

    protected Calculable calculable;

    protected ExpressionCalculator( String expression, String... variableNames ) throws UnparsableExpressionException, UnknownFunctionException {
        this.calculable = buildCalculable( expression, variableNames );
    }


    protected Calculable buildCalculable( String expression, String... variableNames ) throws UnknownFunctionException, UnparsableExpressionException {
        ExpressionBuilder expressionBuilder = new ExpressionBuilder( expression );
        if ( variableNames != null ) {
            expressionBuilder.withVariableNames( variableNames );
        }
        return expressionBuilder.build();
    }


}
