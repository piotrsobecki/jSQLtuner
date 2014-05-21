package pl.piotrsukiennik.tuner.model.query.impl;

import pl.piotrsukiennik.tuner.cache.QueryInvalidaton;
import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.model.schema.Procedure;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 21:04
 */
@Entity
public class CallQuery extends Query {
    private Procedure procedure;

    @ManyToOne(cascade = CascadeType.MERGE)
    public Procedure getProcedure() {
        return procedure;
    }

    public void setProcedure( Procedure procedure ) {
        this.procedure = procedure;
    }


    @Override
    public <R> R invalidates( QueryInvalidaton<R> invalidator ) {
        return invalidator.invalidates( this );
    }
}
