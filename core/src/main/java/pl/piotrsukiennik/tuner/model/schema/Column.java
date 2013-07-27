package pl.piotrsukiennik.tuner.model.schema;

import pl.piotrsukiennik.tuner.model.ValueEntity;

import javax.persistence.*;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 20:57
 */
@Entity
public class Column extends ValueEntity {


    private Type type;
   @ManyToOne
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
