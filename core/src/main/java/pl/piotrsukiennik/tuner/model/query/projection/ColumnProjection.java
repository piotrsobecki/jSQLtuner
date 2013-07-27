package pl.piotrsukiennik.tuner.model.query.projection;

import pl.piotrsukiennik.tuner.model.schema.Column;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 20:58
 */
@Entity
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
public class ColumnProjection extends SourceProjection{
    private Column column;

    @ManyToOne
    public Column getColumn() {
        return column;
    }

    public void setColumn(Column column) {
        this.column = column;
    }
}
