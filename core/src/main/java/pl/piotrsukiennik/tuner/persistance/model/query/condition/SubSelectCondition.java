package pl.piotrsukiennik.tuner.persistance.model.query.condition;

import pl.piotrsukiennik.tuner.persistance.model.ValueEntity;
import pl.piotrsukiennik.tuner.persistance.model.query.SelectQuery;

import javax.persistence.*;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 21:10
 */
@Entity
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
public class SubSelectCondition extends Condition {
    private SelectQuery subQuery;

    @ManyToOne(cascade = CascadeType.ALL)
    public SelectQuery getSubQuery() {
        return subQuery;
    }

    public void setSubQuery(SelectQuery subQuery) {
        this.subQuery = subQuery;
    }
}
