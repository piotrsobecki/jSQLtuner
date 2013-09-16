package pl.piotrsukiennik.tuner.persistance.model.query.condition;

import javax.persistence.*;
import java.util.Set;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 21:10
 */
@Entity
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
public class ListCondition extends Condition {
    private Set<Condition> subConditions;

    @ManyToMany(cascade = CascadeType.ALL)

    public Set<Condition> getSubConditions() {
        return subConditions;
    }

    public void setSubConditions(Set<Condition> subConditions) {
        this.subConditions = subConditions;
    }
}
