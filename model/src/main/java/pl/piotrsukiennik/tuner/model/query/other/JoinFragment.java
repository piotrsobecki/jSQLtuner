package pl.piotrsukiennik.tuner.model.query.other;

import pl.piotrsukiennik.tuner.model.ValueEntity;
import pl.piotrsukiennik.tuner.model.query.expression.Expression;
import pl.piotrsukiennik.tuner.model.query.source.Source;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Author: Piotr Sukiennik
 * Date: 19.09.13
 * Time: 23:42
 */
@Entity
public class JoinFragment extends ValueEntity {

    private Source source;

    private Expression on;


    @ManyToOne(cascade = CascadeType.MERGE, optional = false)
    public Source getSource() {
        return source;
    }

    public void setSource( Source source ) {
        this.source = source;
    }

    @ManyToOne(cascade = CascadeType.MERGE, optional = false)
    public Expression getOn() {
        return on;
    }

    public void setOn( Expression on ) {
        this.on = on;
    }
}
