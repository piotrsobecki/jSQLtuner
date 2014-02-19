package pl.piotrsukiennik.tuner.model.other;

import pl.piotrsukiennik.tuner.model.ValueEntity;

import javax.persistence.*;
import java.util.Set;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 21:09
 */
@Entity
@Table(name = "ValuesEntity")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Values extends ValueEntity {
    private Set<ColumnValue> columnValues;

    @OneToMany(cascade = CascadeType.MERGE)
    public Set<ColumnValue> getColumnValues() {
        return columnValues;
    }

    public void setColumnValues( Set<ColumnValue> columnValues ) {
        this.columnValues = columnValues;
    }

    @Override
    public String toString() {

        String out = "";
        String comma = "";
        for ( ColumnValue columnValue : columnValues ) {
            out += comma + columnValue.getColumn().getValue() + "=" + columnValue.getValue();
            comma = ",";
        }
        return out;
    }
}
