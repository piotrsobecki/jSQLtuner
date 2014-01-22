package pl.piotrsukiennik.tuner.model.schema;

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
@javax.persistence.Table(name = "TableEntity")
public class Table extends ValueEntity {


    public Table() {
        int a = 1;
    }

    private Schema schema;

    @ManyToOne(cascade = CascadeType.MERGE, optional = false)
    public Schema getSchema() {
        return schema;
    }

    public void setSchema( Schema schema ) {
        this.schema = schema;
    }

}
