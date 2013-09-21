package pl.piotrsukiennik.tuner.persistance.model.query.other;

import pl.piotrsukiennik.tuner.persistance.model.ValueEntity;
import pl.piotrsukiennik.tuner.persistance.model.query.condition.Condition;

import javax.persistence.*;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 20:57
 */
@Entity
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
public class OrderByFragment extends ValueEntity implements SortableByPosition {
    public enum Order{ASC,DESC}

    private Condition orderByExpression;
    private Order orderDirection;
    private int position;


    @Enumerated(value = EnumType.STRING)
    public Order getOrderDirection() {
        return orderDirection;
    }

    public void setOrderDirection(Order orderDirection) {
        this.orderDirection = orderDirection;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public Condition getOrderByExpression() {
        return orderByExpression;
    }

    public void setOrderByExpression(Condition orderByExpression) {
        this.orderByExpression = orderByExpression;
    }



    @Override
    public int getPosition() {
        return position;
    }
    @Override
    public void setPosition(int position) {
        this.position = position;
    }
}
