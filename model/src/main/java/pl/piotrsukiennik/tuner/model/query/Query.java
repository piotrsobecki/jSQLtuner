package pl.piotrsukiennik.tuner.model.query;

import pl.piotrsukiennik.tuner.model.ValueEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 20:54
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Query extends ValueEntity implements ValueQuery {
    private String hash;


    @Column(length = 32)
    public String getHash() {
        return hash;
    }

    public void setHash( String hash ) {
        this.hash = hash;
    }

}
