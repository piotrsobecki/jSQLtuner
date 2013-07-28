package pl.piotrsukiennik.tuner.persistance.model.query.projection;

import pl.piotrsukiennik.tuner.persistance.model.schema.Function;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 20:59
 */
@Entity
public class FunctionProjection extends ColumnProjection {
    private Function function;

    @ManyToOne
    public Function getFunction() {
        return function;
    }

    public void setFunction(Function function) {
        this.function = function;
    }
}
