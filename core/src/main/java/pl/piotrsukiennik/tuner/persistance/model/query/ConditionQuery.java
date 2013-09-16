package pl.piotrsukiennik.tuner.persistance.model.query;

import pl.piotrsukiennik.tuner.persistance.model.query.condition.Condition;

import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
import java.util.Set;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 21:00
 */
public interface ConditionQuery {

    Condition getWhereCondition();
    void setWhereCondition(Condition whereCondition);
}
