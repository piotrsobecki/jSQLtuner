package pl.piotrsukiennik.tuner.model.other;

import pl.piotrsukiennik.tuner.model.ValueEntity;
import pl.piotrsukiennik.tuner.model.expression.OperatorExpression;
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

    private OperatorExpression expression;


    @ManyToOne(cascade = CascadeType.MERGE)
    public OperatorExpression getExpression() {
        return expression;
    }

    public void setExpression( OperatorExpression expression ) {
        this.expression = expression;
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
