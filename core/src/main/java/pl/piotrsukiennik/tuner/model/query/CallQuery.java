package pl.piotrsukiennik.tuner.model.query;

import pl.piotrsukiennik.tuner.model.schema.Procedure;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 21:04
 */
@Entity
public class CallQuery extends Query{
    private Procedure procedure;

    @ManyToOne
    public Procedure getProcedure() {
        return procedure;
    }

    public void setProcedure(Procedure procedure) {
        this.procedure = procedure;
    }
}
