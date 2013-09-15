package pl.piotrsukiennik.tuner.persistance.model.query;

import pl.piotrsukiennik.tuner.persistance.model.query.condition.Condition;
import pl.piotrsukiennik.tuner.persistance.model.query.other.ColumnValue;
import pl.piotrsukiennik.tuner.persistance.model.query.other.Values;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.Set;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 20:55
 */
@Entity
public class UpdateQuery extends WriteQuery implements ConditionQuery{
    private Set<ColumnValue> columnValues;
    private Set<Condition> conditions;

    @Override
    @ManyToMany(cascade = CascadeType.ALL)
    public Set<Condition> getConditions() {
        return conditions;
    }

    @Override
    public void setConditions(Set<Condition> conditions) {
        this.conditions = conditions;
    }

    @OneToMany(cascade = CascadeType.ALL)
    public Set<ColumnValue> getColumnValues() {
        return columnValues;
    }

    public void setColumnValues(Set<ColumnValue> columnValues) {
        this.columnValues = columnValues;
    }

}
