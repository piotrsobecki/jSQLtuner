package pl.piotrsukiennik.tuner.model.query.other;

import pl.piotrsukiennik.tuner.model.ValueEntity;
import pl.piotrsukiennik.tuner.model.query.condition.Condition;
import pl.piotrsukiennik.tuner.model.schema.Column;

import javax.persistence.*;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 21:10
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class ColumnValue extends ValueEntity {

    private Condition condition;


    @ManyToOne(cascade = CascadeType.MERGE)
    public Condition getCondition() {
        return condition;
    }

    public void setCondition( Condition condition ) {
        this.condition = condition;
    }

    private Column column;

    @ManyToOne(cascade = CascadeType.MERGE)
    public Column getColumn() {
        return column;
    }

    public void setColumn( Column column ) {
        this.column = column;
    }
}
