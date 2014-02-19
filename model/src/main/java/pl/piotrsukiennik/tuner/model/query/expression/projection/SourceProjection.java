package pl.piotrsukiennik.tuner.model.query.expression.projection;

import pl.piotrsukiennik.tuner.model.query.source.Source;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 21:21
 */
@Entity
public class SourceProjection extends Projection {
    private Source source;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    public Source getSource() {
        return source;
    }

    public void setSource( Source source ) {
        this.source = source;
    }
}
