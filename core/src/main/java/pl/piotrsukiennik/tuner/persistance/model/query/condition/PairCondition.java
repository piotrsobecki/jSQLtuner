package pl.piotrsukiennik.tuner.persistance.model.query.condition;


import pl.piotrsukiennik.tuner.persistance.model.schema.ConditionOperator;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 21:10
 */
@Entity
public class PairCondition extends Condition {

    private Condition leftCondition;

    private Condition rightCondition;

    private ConditionOperator operator;

    @ManyToOne
    public Condition getLeftCondition() {
        return leftCondition;
    }

    public void setLeftCondition(Condition leftCondition) {
        this.leftCondition = leftCondition;
    }

    @ManyToOne
    public Condition getRightCondition() {
        return rightCondition;
    }

    public void setRightCondition(Condition rightCondition) {
        this.rightCondition = rightCondition;
    }

    @ManyToOne
    public ConditionOperator getOperator() {
        return operator;
    }

    public void setOperator(ConditionOperator operator) {
        this.operator = operator;
    }
}
