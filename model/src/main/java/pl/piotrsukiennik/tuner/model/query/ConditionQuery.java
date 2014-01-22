package pl.piotrsukiennik.tuner.model.query;

import pl.piotrsukiennik.tuner.model.query.condition.Condition;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 21:00
 */
public interface ConditionQuery {

    Condition getWhereCondition();

    void setWhereCondition( Condition whereCondition );
}
