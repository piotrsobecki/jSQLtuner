package pl.piotrsukiennik.tuner.model.expression.projection;

import pl.piotrsukiennik.tuner.model.schema.Column;
import pl.piotrsukiennik.tuner.model.source.Source;

import javax.persistence.*;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 20:58
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class ColumnProjection extends Projection {
    private Source source;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    public Source getSource() {
        return source;
    }

    public void setSource( Source source ) {
        this.source = source;
    }

    private Column column;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    public Column getColumn() {
        return column;
    }

    public void setColumn( Column column ) {
        this.column = column;
    }
}
