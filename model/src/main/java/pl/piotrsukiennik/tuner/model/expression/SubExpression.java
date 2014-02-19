package pl.piotrsukiennik.tuner.model.expression;

import javax.persistence.*;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 21:10
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class SubExpression extends OperatorExpression {
    private Expression subExpression;

    @ManyToOne(cascade = CascadeType.MERGE)
    public Expression getSubExpression() {
        return subExpression;
    }

    public void setSubExpression( Expression subExpression ) {
        this.subExpression = subExpression;
    }
}
