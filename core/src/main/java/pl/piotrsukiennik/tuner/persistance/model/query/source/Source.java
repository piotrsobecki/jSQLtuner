package pl.piotrsukiennik.tuner.persistance.model.query.source;

import pl.piotrsukiennik.tuner.persistance.model.ValueEntity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 21:01
 */
@Entity
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
public abstract class Source extends ValueEntity {
    private String alias;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
