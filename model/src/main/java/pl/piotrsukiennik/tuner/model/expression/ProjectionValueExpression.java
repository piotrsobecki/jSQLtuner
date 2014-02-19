package pl.piotrsukiennik.tuner.model.expression;

import pl.piotrsukiennik.tuner.model.expression.projection.Projection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 21:11
 */
@Entity
public class ProjectionValueExpression extends OperatorExpression {

    private Projection projection;

    @ManyToOne(cascade = CascadeType.MERGE)
    public Projection getProjection() {
        return projection;
    }

    public void setProjection( Projection projection ) {
        this.projection = projection;
    }
}
