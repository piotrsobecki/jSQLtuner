package pl.piotrsukiennik.tuner.model.query.impl;

import pl.piotrsukiennik.tuner.cache.QueryInvalidatonVisitor;
import pl.piotrsukiennik.tuner.model.query.InsertQuery;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 21:08
 */
@Entity
public class InsertAsSelectQuery extends InsertQuery {
    private SelectQuery selectQuery;

    @ManyToOne(cascade = CascadeType.MERGE)
    public SelectQuery getSelectQuery() {
        return selectQuery;
    }

    public void setSelectQuery( SelectQuery selectQuery ) {
        this.selectQuery = selectQuery;
    }

    @Override
    public <R> R accept( QueryInvalidatonVisitor<R> invalidator ) {
        return invalidator.visit( this );
    }
}
