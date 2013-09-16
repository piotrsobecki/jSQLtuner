package pl.piotrsukiennik.tuner.persistance.model.query;

import pl.piotrsukiennik.tuner.persistance.model.query.condition.Condition;
import pl.piotrsukiennik.tuner.persistance.model.query.other.ColumnValue;
import pl.piotrsukiennik.tuner.persistance.model.schema.*;
import pl.piotrsukiennik.tuner.persistance.model.schema.Column;

import javax.persistence.*;
import java.util.Set;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 20:55
 */
@Entity
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
public class InsertWithValuesQuery extends InsertQuery {


    private Set<Condition> columnValues;


    @OneToMany(cascade = CascadeType.ALL)
    public Set<Condition> getColumnValues() {
        return columnValues;
    }

    public void setColumnValues(Set<Condition> columnValues) {
        this.columnValues = columnValues;
    }


}
