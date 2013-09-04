package pl.piotrsukiennik.tuner.persistance.model.query;

import pl.piotrsukiennik.tuner.persistance.model.ValueEntity;
import pl.piotrsukiennik.tuner.parser.IQuery;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 20:54
 */
@Entity
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
public abstract class Query  extends ValueEntity implements IQuery{
    private String hash;


    @Column(length = 32)
    public String getHash() {
        return hash;
    }
    public void setHash(String hash) {
        this.hash = hash;
    }

}
