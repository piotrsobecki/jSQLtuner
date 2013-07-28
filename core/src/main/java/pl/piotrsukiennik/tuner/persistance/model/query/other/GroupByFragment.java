package pl.piotrsukiennik.tuner.persistance.model.query.other;

import pl.piotrsukiennik.tuner.persistance.model.ValueEntity;
import pl.piotrsukiennik.tuner.persistance.model.query.projection.Projection;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;


/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 20:57
 */
@Entity
public class GroupByFragment extends ValueEntity implements SortableByPosition{
    private Projection projection;
    private int position;

    @ManyToOne
    public Projection getProjection() {
        return projection;
    }

    public void setProjection(Projection projection) {
        this.projection = projection;
    }

    @Override
    public int getPosition() {
        return position;
    }
    @Override
    public void setPosition(int position) {
        this.position = position;
    }
}
