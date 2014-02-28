package pl.piotrsukiennik.tuner.model.query.impl;

import pl.piotrsukiennik.tuner.cache.QueryInvalidator;
import pl.piotrsukiennik.tuner.model.expression.OperatorExpression;
import pl.piotrsukiennik.tuner.model.query.ConditionQuery;
import pl.piotrsukiennik.tuner.model.query.WriteQuery;
import pl.piotrsukiennik.tuner.model.source.TableSource;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 20:56
 */
@Entity
public class DeleteQuery extends WriteQuery implements ConditionQuery {
    private TableSource tableSource;

    @ManyToOne(cascade = CascadeType.MERGE)
    public TableSource getTableSource() {
        return tableSource;
    }

    public void setTableSource( TableSource tableSource ) {
        this.tableSource = tableSource;
    }

    private OperatorExpression whereExpression;


    @ManyToOne(cascade = CascadeType.MERGE)
    public OperatorExpression getWhereExpression() {
        return whereExpression;
    }

    public void setWhereExpression( OperatorExpression whereExpression ) {
        this.whereExpression = whereExpression;
    }

    @Override
    public <R> R invalidates( QueryInvalidator<R> invalidator ) {
        return invalidator.invalidates( this );
    }
}
