package pl.piotrsukiennik.tuner.model.schema;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import pl.piotrsukiennik.tuner.model.ValueEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 20:57
 */
@Entity
@javax.persistence.Table(name = "ColumnEntity")
public class Column extends ValueEntity {
    private Table table;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, optional = false)
    @Fetch(FetchMode.SELECT)
    public Table getTable() {
        return table;
    }

    public void setTable( Table table ) {
        this.table = table;
    }

    private Type type;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    public Type getType() {
        return type;
    }

    public void setType( Type type ) {
        this.type = type;
    }
}
