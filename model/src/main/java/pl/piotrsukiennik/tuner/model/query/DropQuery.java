package pl.piotrsukiennik.tuner.model.query;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 20:55
 */
@Entity
@Inheritance( strategy = InheritanceType.SINGLE_TABLE )
public abstract class DropQuery extends WriteQuery {
    private String type;

    public String getType() {
        return type;
    }

    public void setType( String type ) {
        this.type = type;
    }
}
