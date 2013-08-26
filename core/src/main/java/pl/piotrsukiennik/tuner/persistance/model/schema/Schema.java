package pl.piotrsukiennik.tuner.persistance.model.schema;

import pl.piotrsukiennik.tuner.persistance.model.ValueEntity;

import javax.persistence.*;
import javax.persistence.Table;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 20:57
 */
@Entity
@Table(name = "SchemaEntity")
public class Schema extends ValueEntity {


    private Database database;

    @ManyToOne(cascade = CascadeType.ALL,optional = false)
    public Database getDatabase() {
        return database;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }
}
