package pl.piotrsukiennik.tuner.persistance.model.query.other;

import pl.piotrsukiennik.tuner.persistance.model.ValueEntity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 21:09
 */
@Entity
@Table(name = "ValuesEntity")
public class Values extends ValueEntity {
    private List<ColumnValue> columnValues;

    @OneToMany
    public List<ColumnValue> getColumnValues() {
        return columnValues;
    }

    public void setColumnValues(List<ColumnValue> columnValues) {
        this.columnValues = columnValues;
    }
}
