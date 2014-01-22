package pl.piotrsukiennik.tuner.model.query;

import pl.piotrsukiennik.tuner.model.ValueEntity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import java.sql.Timestamp;

/**
 * Author: Piotr Sukiennik
 * Date: 15.09.13
 * Time: 13:49
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class QueryExecution extends ValueEntity {
    private Timestamp timestamp;

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp( Timestamp timestamp ) {
        this.timestamp = timestamp;
    }
}
