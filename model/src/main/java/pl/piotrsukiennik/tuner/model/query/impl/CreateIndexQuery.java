package pl.piotrsukiennik.tuner.model.query.impl;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import pl.piotrsukiennik.tuner.cache.QueryInvalidator;
import pl.piotrsukiennik.tuner.model.query.CreateQuery;
import pl.piotrsukiennik.tuner.model.schema.Index;

import javax.persistence.*;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 20:55
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class CreateIndexQuery extends CreateQuery {

    private pl.piotrsukiennik.tuner.model.schema.Table table;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @LazyCollection(LazyCollectionOption.FALSE)
    public pl.piotrsukiennik.tuner.model.schema.Table getTable() {
        return table;
    }

    public void setTable( pl.piotrsukiennik.tuner.model.schema.Table table ) {
        this.table = table;
    }

    private Index index;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @LazyCollection(LazyCollectionOption.FALSE)
    public Index getIndex() {
        return index;
    }

    public void setIndex( Index index ) {
        this.index = index;
    }

    @Override
    public <R> R invalidates( QueryInvalidator<R> invalidator ) {
        return invalidator.invalidates( this );
    }
}
