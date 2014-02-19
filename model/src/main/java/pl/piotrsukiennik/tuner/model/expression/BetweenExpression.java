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
public class BetweenExpression extends OperatorExpression {

    private Expression leftExpression;

    private Expression startExpression;

    private Expression endExpression;


    @ManyToOne(cascade = CascadeType.MERGE)
    public Expression getLeftExpression() {
        return leftExpression;
    }

    public void setLeftExpression( Expression leftExpression ) {
        this.leftExpression = leftExpression;
    }

    @ManyToOne(cascade = CascadeType.MERGE)
    public Expression getStartExpression() {
        return startExpression;
    }

    public void setStartExpression( Expression startExpression ) {
        this.startExpression = startExpression;
    }

    @ManyToOne(cascade = CascadeType.MERGE)
    public Expression getEndExpression() {
        return endExpression;
    }

    public void setEndExpression( Expression endExpression ) {
        this.endExpression = endExpression;
    }
}
