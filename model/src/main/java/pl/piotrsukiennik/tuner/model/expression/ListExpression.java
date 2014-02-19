package pl.piotrsukiennik.tuner.model.expression;

import javax.persistence.*;
import java.util.Set;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 21:10
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class ListExpression extends OperatorExpression {
    private Set<Expression> subExpressions;

    @ManyToMany(cascade = CascadeType.MERGE)
    public Set<Expression> getSubExpressions() {
        return subExpressions;
    }

    public void setSubExpressions( Set<Expression> subExpressions ) {
        this.subExpressions = subExpressions;
    }
}
