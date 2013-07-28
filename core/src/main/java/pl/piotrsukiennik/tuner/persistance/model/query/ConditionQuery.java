package pl.piotrsukiennik.tuner.persistance.model.query;

import pl.piotrsukiennik.tuner.persistance.model.query.condition.Condition;

import java.util.Set;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 21:00
 */
public interface ConditionQuery {
    Set<Condition> getConditions();
    void setConditions(Set<Condition> conditions);
}
