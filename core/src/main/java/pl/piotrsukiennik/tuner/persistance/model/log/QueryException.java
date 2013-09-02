package pl.piotrsukiennik.tuner.persistance.model.log;

import pl.piotrsukiennik.tuner.persistance.model.ValueEntity;
import pl.piotrsukiennik.tuner.persistance.model.schema.Column;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Lob;
import java.sql.Timestamp;
import java.util.Date;

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

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
    @Lob
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
