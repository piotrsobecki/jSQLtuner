package pl.piotrsukiennik.tuner.persistance.model.query.condition;

import pl.piotrsukiennik.tuner.persistance.model.ValueEntity;

import javax.persistence.*;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 21:10
 */
@Entity
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
public class Condition extends ValueEntity {
    private ConditionOperator operator;

    @Enumerated(EnumType.STRING)
    public ConditionOperator getOperator() {
        return operator;
    }

    public void setOperator(ConditionOperator operator) {
        this.operator = operator;
    }

    private boolean inverse;

    public boolean isInverse() {
        return inverse;
    }

    public void setInverse(boolean inverse) {
        this.inverse = inverse;
    }
}
