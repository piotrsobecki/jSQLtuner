package pl.piotrsukiennik.tuner.model.query;

import pl.piotrsukiennik.tuner.model.query.condition.Condition;
import pl.piotrsukiennik.tuner.model.query.source.TableSource;

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

    private Set<Condition> conditions;

    @ManyToOne
    public TableSource getTableSource() {
        return tableSource;
    }

    public void setTableSource(TableSource tableSource) {
        this.tableSource = tableSource;
    }

    @Override
    @ManyToMany
    public Set<Condition> getConditions() {
        return conditions;
    }

    @Override
    public void setConditions(Set<Condition> conditions) {
        this.conditions = conditions;
    }
}
