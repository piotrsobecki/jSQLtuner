package pl.piotrsukiennik.tuner.persistance.model.query.other;

import pl.piotrsukiennik.tuner.persistance.model.ValueEntity;
import pl.piotrsukiennik.tuner.persistance.model.query.condition.Condition;
import pl.piotrsukiennik.tuner.persistance.model.schema.Column;

import javax.persistence.*;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 21:10
 */
@Entity
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
public class ColumnValue extends ValueEntity {

    private Condition condition;


    @ManyToOne(cascade = CascadeType.ALL)
    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    private Column column;

    @ManyToOne(cascade = CascadeType.ALL)
    public Column getColumn() {
        return column;
    }

    public void setColumn(Column column) {
        this.column = column;
    }
}
