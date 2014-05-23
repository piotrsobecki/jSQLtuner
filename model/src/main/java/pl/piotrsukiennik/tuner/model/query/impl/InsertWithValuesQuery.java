package pl.piotrsukiennik.tuner.model.query.impl;

import pl.piotrsukiennik.tuner.cache.QueryInvalidatonVisitor;
import pl.piotrsukiennik.tuner.model.expression.Expression;
import pl.piotrsukiennik.tuner.model.query.InsertQuery;

import javax.persistence.*;
import java.util.Set;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 20:55
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class InsertWithValuesQuery extends InsertQuery {

    private Set<Expression> columnValues;


    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    public Set<Expression> getColumnValues() {
        return columnValues;
    }

    public void setColumnValues( Set<Expression> columnValues ) {
        this.columnValues = columnValues;
    }

    @Override
    public <R> R accept( QueryInvalidatonVisitor<R> invalidator ) {
        return invalidator.visit( this );
    }

}
