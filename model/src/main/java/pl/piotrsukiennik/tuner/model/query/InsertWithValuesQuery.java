package pl.piotrsukiennik.tuner.model.query;

import pl.piotrsukiennik.tuner.model.query.condition.Condition;

import javax.persistence.*;
import java.util.Set;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 20:55
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class InsertWithValuesQuery extends InsertQuery {


    private Set<Condition> columnValues;


    @OneToMany(cascade = CascadeType.MERGE)
    public Set<Condition> getColumnValues() {
        return columnValues;
    }

    public void setColumnValues( Set<Condition> columnValues ) {
        this.columnValues = columnValues;
    }


}
