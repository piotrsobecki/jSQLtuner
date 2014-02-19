package pl.piotrsukiennik.tuner.model.query;

import pl.piotrsukiennik.tuner.model.expression.OperatorExpression;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 21:00
 */
public interface ConditionQuery {

    OperatorExpression getWhereExpression();

    void setWhereExpression( OperatorExpression whereExpression );
}
