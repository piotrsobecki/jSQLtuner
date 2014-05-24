package pl.piotrsukiennik.tuner.service.impl;


import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import pl.piotrsukiennik.tuner.service.DataSourceFitnessService;
import pl.piotrsukiennik.tuner.service.DataSourceSelectable;

/**
 * @author Piotr Sukiennik
 * @date 13.02.14
 */
public class DataSourceFitnessServiceImpl implements DataSourceFitnessService {

    private Expression expression;

    public DataSourceFitnessServiceImpl( String expressionStr )  {
        SpelExpressionParser spelExpressionParser = new SpelExpressionParser();
        expression = spelExpressionParser.parseExpression(expressionStr );

    }

    @Override
    public double calc( DataSourceSelectable sourceForQuery ) {
        return expression.getValue( sourceForQuery, Double.class );
    }


}
