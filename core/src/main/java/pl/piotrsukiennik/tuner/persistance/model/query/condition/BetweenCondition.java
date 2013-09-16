package pl.piotrsukiennik.tuner.persistance.model.query.condition;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 21:10
 */
@Entity
public class BetweenCondition extends Condition {

    private Condition leftCondition;

    private Condition startCondition;

    private Condition endCondition;


    @ManyToOne(cascade = CascadeType.ALL)
    public Condition getLeftCondition() {
        return leftCondition;
    }

    public void setLeftCondition(Condition leftCondition) {
        this.leftCondition = leftCondition;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public Condition getStartCondition() {
        return startCondition;
    }

    public void setStartCondition(Condition startCondition) {
        this.startCondition = startCondition;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public Condition getEndCondition() {
        return endCondition;
    }

    public void setEndCondition(Condition endCondition) {
        this.endCondition = endCondition;
    }
}
