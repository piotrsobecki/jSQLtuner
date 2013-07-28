package pl.piotrsukiennik.tuner.persistance.model.query.other;

import pl.piotrsukiennik.tuner.persistance.model.ValueEntity;
import pl.piotrsukiennik.tuner.persistance.model.schema.Column;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 21:10
 */
@Entity
public class ColumnValue extends ValueEntity {
    private Column column;

    @ManyToOne
    public Column getColumn() {
        return column;
    }

    public void setColumn(Column column) {
        this.column = column;
    }
}
