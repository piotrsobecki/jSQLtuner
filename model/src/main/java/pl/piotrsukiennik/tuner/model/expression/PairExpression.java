package pl.piotrsukiennik.tuner.model.expression;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 21:10
 */
@Entity
public class PairExpression extends OperatorExpression {

    private Expression leftExpression;

    private Expression rightExpression;


    @ManyToOne(cascade = CascadeType.MERGE)
    public Expression getLeftExpression() {
        return leftExpression;
    }

    public void setLeftExpression( Expression leftExpression ) {
        this.leftExpression = leftExpression;
    }

    @ManyToOne(cascade = CascadeType.MERGE)
    public Expression getRightExpression() {
        return rightExpression;
    }

    public void setRightExpression( Expression rightExpression ) {
        this.rightExpression = rightExpression;
    }


}
