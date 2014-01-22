package pl.piotrsukiennik.tuner.model.query.other;

import pl.piotrsukiennik.tuner.model.ValueEntity;
import pl.piotrsukiennik.tuner.model.query.projection.Projection;

import javax.persistence.*;


/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 20:57
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class GroupByFragment extends ValueEntity implements SortableByPosition {
    private Projection projection;

    private int position;

    @ManyToOne(cascade = CascadeType.MERGE)
    public Projection getProjection() {
        return projection;
    }

    public void setProjection( Projection projection ) {
        this.projection = projection;
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
