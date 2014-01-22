package pl.piotrsukiennik.tuner.model.query.source;

import pl.piotrsukiennik.tuner.model.query.SelectQuery;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 21:02
 */
@Entity
public class SubQuerySource extends Source {
    private SelectQuery selectQuery;

    @ManyToOne(cascade = CascadeType.MERGE)
    public SelectQuery getSelectQuery() {
        return selectQuery;
    }

    public void setSelectQuery( SelectQuery selectQuery ) {
        this.selectQuery = selectQuery;
    }
}
