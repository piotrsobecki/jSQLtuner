package pl.piotrsukiennik.tuner.persistance.model.query.projection;

import pl.piotrsukiennik.tuner.persistance.model.schema.Column;

import javax.persistence.*;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 20:58
 */
@Entity
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
public class ColumnProjection extends SourceProjection{
    private Column column;

    @ManyToOne(cascade = CascadeType.ALL)
    public Column getColumn() {
        return column;
    }

    public void setColumn(Column column) {
        this.column = column;
    }
}
