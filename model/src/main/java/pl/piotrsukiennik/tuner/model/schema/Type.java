package pl.piotrsukiennik.tuner.model.schema;

import pl.piotrsukiennik.tuner.model.ValueEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 21:20
 */
@Entity
@javax.persistence.Table(name = "TypeEntity")
public class Type extends ValueEntity {

    private Database database;

    @ManyToOne
    public Database getDatabase() {
        return database;
    }

    public void setDatabase( Database database ) {
        this.database = database;
    }
}
