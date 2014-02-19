package pl.piotrsukiennik.tuner.model.query.other;

import pl.piotrsukiennik.tuner.model.ValueEntity;
import pl.piotrsukiennik.tuner.model.query.expression.Expression;

import javax.persistence.*;


/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 20:57
 */
@Entity
@Inheritance( strategy = InheritanceType.SINGLE_TABLE )
public class GroupByFragment extends ValueEntity implements SortableByPosition {
    private Expression expression;

    private int position;

    @ManyToOne( cascade = { CascadeType.MERGE, CascadeType.PERSIST } )
    public Expression getExpression() {
        return expression;
    }

    public void setExpression( Expression expression ) {
        this.expression = expression;
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public void setPosition( int position ) {
        this.position = position;
    }
}
