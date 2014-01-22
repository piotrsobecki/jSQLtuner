package pl.piotrsukiennik.tuner.model.schema;

import pl.piotrsukiennik.tuner.model.ValueEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 20:59
 */
@Entity
@javax.persistence.Table(name = "FunctionEntity")
public class Function extends ValueEntity {
    private Database database;

    @ManyToOne(cascade = CascadeType.MERGE, optional = false)
    public Database getDatabase() {
        return database;
    }

    public void setDatabase( Database database ) {
        this.database = database;
    }
}
