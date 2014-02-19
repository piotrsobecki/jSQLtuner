package pl.piotrsukiennik.tuner.model.query.expression;

import javax.persistence.*;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 21:10
 */
@Entity
@Table(name = "ConditionEntity")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class OperatorExpression extends Expression {
    private ExpressionOperator operator;

    @Enumerated(EnumType.STRING)
    public ExpressionOperator getOperator() {
        return operator;
    }

    public void setOperator( ExpressionOperator operator ) {
        this.operator = operator;
    }

    private boolean inverse;

    public boolean isInverse() {
        return inverse;
    }

    public void setInverse( boolean inverse ) {
        this.inverse = inverse;
    }
}
