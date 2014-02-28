package pl.piotrsukiennik.tuner.model.query.impl;

import pl.piotrsukiennik.tuner.cache.QueryInvalidator;
import pl.piotrsukiennik.tuner.model.query.DropQuery;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 20:55
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class GenericDropQuery extends DropQuery {
    private String name;

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }


    @Override
    public <R> R invalidates( QueryInvalidator<R> invalidator ) {
        return invalidator.invalidates( this );
    }
}
