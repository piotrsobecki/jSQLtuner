package pl.piotrsukiennik.tuner.model.query.expression;

import pl.piotrsukiennik.tuner.model.query.SelectQuery;

import javax.persistence.*;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 21:10
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class SubSelectExpression extends OperatorExpression {
    private SelectQuery subQuery;

    @ManyToOne(cascade = CascadeType.MERGE)
    public SelectQuery getSubQuery() {
        return subQuery;
    }

    public void setSubQuery( SelectQuery subQuery ) {
        this.subQuery = subQuery;
    }
}
