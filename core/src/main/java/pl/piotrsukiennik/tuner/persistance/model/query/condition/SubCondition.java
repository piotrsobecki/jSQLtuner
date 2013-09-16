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
public class SubCondition extends Condition {
    private Condition subCondition;

    @ManyToOne(cascade = CascadeType.ALL)
    public Condition getSubCondition() {
        return subCondition;
    }

    public void setSubCondition(Condition subCondition) {
        this.subCondition = subCondition;
    }
}
