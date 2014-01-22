package pl.piotrsukiennik.tuner.model.query.condition;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 21:10
 */
@Entity
public class PairCondition extends Condition {

    private Condition leftCondition;

    private Condition rightCondition;


    @ManyToOne(cascade = CascadeType.MERGE)
    public Condition getLeftCondition() {
        return leftCondition;
    }

    public void setLeftCondition( Condition leftCondition ) {
        this.leftCondition = leftCondition;
    }

    @ManyToOne(cascade = CascadeType.MERGE)
    public Condition getRightCondition() {
        return rightCondition;
    }

    public void setRightCondition( Condition rightCondition ) {
        this.rightCondition = rightCondition;
    }


}
