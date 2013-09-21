package pl.piotrsukiennik.tuner.persistance.model.query.other;

import pl.piotrsukiennik.tuner.persistance.model.ValueEntity;
import pl.piotrsukiennik.tuner.persistance.model.query.condition.Condition;
import pl.piotrsukiennik.tuner.persistance.model.query.source.Source;

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

    private Condition on;


    @ManyToOne(cascade = CascadeType.ALL,optional = false)
    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    @ManyToOne(cascade = CascadeType.ALL,optional = false)
    public Condition getOn() {
        return on;
    }

    public void setOn(Condition on) {
        this.on = on;
    }
}
