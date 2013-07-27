package pl.piotrsukiennik.tuner.model.query.condition;

import pl.piotrsukiennik.tuner.model.query.projection.Projection;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 21:11
 */
@Entity
public class ProjectionValueCondition extends Condition {

    private Projection projection;

    @ManyToOne
    public Projection getProjection() {
        return projection;
    }

    public void setProjection(Projection projection) {
        this.projection = projection;
    }
}
