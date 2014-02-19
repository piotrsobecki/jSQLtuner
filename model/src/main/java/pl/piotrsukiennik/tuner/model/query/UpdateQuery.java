package pl.piotrsukiennik.tuner.model.query;

import pl.piotrsukiennik.tuner.model.query.expression.OperatorExpression;
import pl.piotrsukiennik.tuner.model.query.other.ColumnValue;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Set;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 20:55
 */
@Entity
public class UpdateQuery extends WriteQuery implements ConditionQuery {
    private Set<ColumnValue> columnValues;

    private OperatorExpression whereExpression;


    @ManyToOne(cascade = { CascadeType.ALL })
    public OperatorExpression getWhereExpression() {
        return whereExpression;
    }

    public void setWhereExpression( OperatorExpression whereExpression ) {
        this.whereExpression = whereExpression;
    }

    @OneToMany( cascade = { CascadeType.ALL } )
    public Set<ColumnValue> getColumnValues() {
        return columnValues;
    }

    public void setColumnValues( Set<ColumnValue> columnValues ) {
        this.columnValues = columnValues;
    }

}
