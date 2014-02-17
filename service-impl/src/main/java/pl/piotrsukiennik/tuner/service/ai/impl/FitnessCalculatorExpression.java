package pl.piotrsukiennik.tuner.service.ai.impl;

import de.congrace.exp4j.UnknownFunctionException;
import de.congrace.exp4j.UnparsableExpressionException;
import pl.piotrsukiennik.tuner.service.ai.DataSourceSelectable;
import pl.piotrsukiennik.tuner.service.ai.FitnessCalculator;

/**
 * @author Piotr Sukiennik
 * @date 13.02.14
 */
public class FitnessCalculatorExpression extends ExpressionCalculator implements FitnessCalculator {

    private static final String VAR_AVERAGE_EXECUTION_TIME = "averageExecutionTime";


    public FitnessCalculatorExpression( String expression ) throws UnparsableExpressionException, UnknownFunctionException {
        super( expression, VAR_AVERAGE_EXECUTION_TIME );
    }

    @Override
    public double calc( DataSourceSelectable sourceForQuery ) {
        calculable.setVariable( VAR_AVERAGE_EXECUTION_TIME, sourceForQuery.getAverageExecutionTime() );
        return calculable.calculate();
    }


}
