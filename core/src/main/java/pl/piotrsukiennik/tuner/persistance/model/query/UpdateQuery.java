package pl.piotrsukiennik.tuner.persistance.model.query;

import pl.piotrsukiennik.tuner.persistance.model.query.condition.Condition;
import pl.piotrsukiennik.tuner.persistance.model.query.other.ColumnValue;
import pl.piotrsukiennik.tuner.persistance.model.query.other.Values;

import javax.persistence.*;
import java.util.Set;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 20:55
 */
@Entity
public class UpdateQuery extends WriteQuery implements ConditionQuery{
    private Set<ColumnValue> columnValues;
    private Condition whereCondition;



    @ManyToOne(cascade = CascadeType.ALL)
    public Condition getWhereCondition() {
        return whereCondition;
    }

    public void setWhereCondition(Condition whereCondition) {
        this.whereCondition = whereCondition;
    }

    @OneToMany(cascade = CascadeType.ALL)
    public Set<ColumnValue> getColumnValues() {
        return columnValues;
    }

    public void setColumnValues(Set<ColumnValue> columnValues) {
        this.columnValues = columnValues;
    }

}
