package pl.piotrsukiennik.tuner.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Service;
import pl.piotrsukiennik.tuner.Property;

/**
 * @author Piotr Sukiennik
 * @date 24.05.14
 */
@Service
public class SpelDataSourceRecommendationServiceImpl extends QueryExecutionComplexityServiceImpl {

    private Expression complexityExpression;

    @Autowired
    public SpelDataSourceRecommendationServiceImpl(@Value( "${" + Property.COMPLEXITY_EXPRESSION + "}" ) String expression) {
        SpelExpressionParser spelExpressionParser = new SpelExpressionParser(  );
        complexityExpression  = spelExpressionParser.parseExpression( expression );
    }

    @Override
    double executionComplexity( ComplexityExpressionParams complexityExpressionParams ) {
        return complexityExpression.getValue( complexityExpressionParams, Double.class );
    }
}
