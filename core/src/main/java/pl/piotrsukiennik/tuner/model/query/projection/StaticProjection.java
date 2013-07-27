package pl.piotrsukiennik.tuner.model.query.projection;

import pl.piotrsukiennik.tuner.model.schema.Type;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 20:58
 */
@Entity
public class StaticProjection extends Projection {
    private Type type;

    @ManyToOne
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
