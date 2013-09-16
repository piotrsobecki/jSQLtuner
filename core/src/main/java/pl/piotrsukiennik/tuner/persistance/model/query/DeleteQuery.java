package pl.piotrsukiennik.tuner.persistance.model.query;

import pl.piotrsukiennik.tuner.persistance.model.query.condition.Condition;
import pl.piotrsukiennik.tuner.persistance.model.query.source.TableSource;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.util.Set;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 20:56
 */
@Entity
public class DeleteQuery extends WriteQuery implements ConditionQuery{
    private TableSource tableSource;

    @ManyToOne(cascade = CascadeType.ALL)
    public TableSource getTableSource() {
        return tableSource;
    }

    public void setTableSource(TableSource tableSource) {
        this.tableSource = tableSource;
    }

    private Condition whereCondition;


    @ManyToOne(cascade = CascadeType.ALL)
    public Condition getWhereCondition() {
        return whereCondition;
    }

    public void setWhereCondition(Condition whereCondition) {
        this.whereCondition = whereCondition;
    }
}
