package pl.piotrsukiennik.tuner.persistance.model.schema;

import pl.piotrsukiennik.tuner.persistance.model.ValueEntity;

import javax.persistence.*;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 20:59
 */
@Entity
@javax.persistence.Table(name = "FunctionEntity")
public class Function extends ValueEntity {
    private Database database;

    @ManyToOne(cascade = CascadeType.ALL,optional = false)
    public Database getDatabase() {
        return database;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }
}
