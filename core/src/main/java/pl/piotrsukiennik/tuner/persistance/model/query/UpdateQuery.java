package pl.piotrsukiennik.tuner.persistance.model.query;

import pl.piotrsukiennik.tuner.persistance.model.query.condition.Condition;
import pl.piotrsukiennik.tuner.persistance.model.query.other.Values;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.Set;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 20:55
 */
@Entity
public class UpdateQuery extends WriteQuery implements ConditionQuery{
    private Set<Values> values;

    private Set<Condition> conditions;

    @Override
    @ManyToMany
    public Set<Condition> getConditions() {
        return conditions;
    }

    @Override
    public void setConditions(Set<Condition> conditions) {
        this.conditions = conditions;
    }
    @ManyToMany
    public Set<Values> getValues() {
        return values;
    }

    public void setValues(Set<Values> values) {
        this.values = values;
    }
}
