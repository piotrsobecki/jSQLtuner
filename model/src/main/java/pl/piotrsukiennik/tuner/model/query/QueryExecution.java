package pl.piotrsukiennik.tuner.model.query;

import pl.piotrsukiennik.tuner.model.ValueEntity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Author: Piotr Sukiennik
 * Date: 15.09.13
 * Time: 13:49
 */
@Entity
@Inheritance( strategy = InheritanceType.SINGLE_TABLE )
public abstract class QueryExecution extends ValueEntity {
    private Timestamp timestamp;

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp( Timestamp timestamp ) {
        this.timestamp = timestamp;
    }


    private Query query;


    @ManyToOne( cascade = CascadeType.MERGE, optional = false )
    public Query getQuery() {
        return query;
    }

    public void setQuery( Query query ) {
        this.query = query;
    }
}
