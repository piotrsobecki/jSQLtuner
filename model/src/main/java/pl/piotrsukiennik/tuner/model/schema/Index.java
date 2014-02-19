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
 * Time: 21:20
 */
@Entity
@javax.persistence.Table( name = "IndexEntity" )
public class Index extends ValueEntity {
    private Table table;

    @ManyToOne( cascade = { CascadeType.PERSIST, CascadeType.MERGE }, optional = false )
    @Fetch( FetchMode.SELECT )
    public Table getTable() {
        return table;
    }

    public void setTable( Table table ) {
        this.table = table;
    }
}
