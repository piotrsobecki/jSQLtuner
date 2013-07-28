package pl.piotrsukiennik.tuner.persistance.model.schema;

import pl.piotrsukiennik.tuner.persistance.model.ValueEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 21:20
 */
@Entity
public class Type extends ValueEntity{

    private Database database;

    @ManyToOne
    public Database getDatabase() {
        return database;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }
}
