package pl.piotrsukiennik.tuner.model.query;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 20:55
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class CreateTableQuery extends WriteQuery {

    private pl.piotrsukiennik.tuner.model.schema.Table table;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @LazyCollection(LazyCollectionOption.FALSE)
    public pl.piotrsukiennik.tuner.model.schema.Table getTable() {
        return table;
    }

    public void setTable( pl.piotrsukiennik.tuner.model.schema.Table table ) {
        this.table = table;
    }

}
