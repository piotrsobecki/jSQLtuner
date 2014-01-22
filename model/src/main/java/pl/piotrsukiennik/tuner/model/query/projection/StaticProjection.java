package pl.piotrsukiennik.tuner.model.query.projection;

import pl.piotrsukiennik.tuner.model.schema.Type;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 20:58
 */
@Entity
public class StaticProjection extends Projection {
    private Type type;

    @ManyToOne(cascade = CascadeType.MERGE)
    public Type getType() {
        return type;
    }

    public void setType( Type type ) {
        this.type = type;
    }
}
