package pl.piotrsukiennik.tuner.model.log;

import pl.piotrsukiennik.tuner.model.ValueEntity;

import javax.persistence.Entity;
import javax.persistence.Lob;
import java.sql.Timestamp;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 22:17
 */
@Entity
public class QueryException extends ValueEntity {
    private String message;

    private Timestamp timestamp;

    @javax.persistence.Column(name = "`timestamp`")
    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp( Timestamp timestamp ) {
        this.timestamp = timestamp;
    }

    @Lob
    public String getMessage() {
        return message;
    }

    public void setMessage( String message ) {
        this.message = message;
    }
}
