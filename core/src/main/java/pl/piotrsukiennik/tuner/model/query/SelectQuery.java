package pl.piotrsukiennik.tuner.model.query;

import pl.piotrsukiennik.tuner.model.query.condition.Condition;
import pl.piotrsukiennik.tuner.model.query.other.GroupByFragment;
import pl.piotrsukiennik.tuner.model.query.other.OrderByFragment;
import pl.piotrsukiennik.tuner.model.query.projection.Projection;
import pl.piotrsukiennik.tuner.model.query.source.Source;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import java.util.List;
import java.util.Set;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 20:54
 */
@Entity
public class SelectQuery extends ReadQuery implements ConditionQuery{


    private List<Projection> projections;


    private Set<Source> sources;

    private Set<Source> joins;




    private List<GroupByFragment> groups;

    private List<OrderByFragment> orders;

    private Set<Condition> conditions;

    private Query parentQuery;

    private Boolean distinct;

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @ManyToMany
    public List<Projection> getProjections() {
        return projections;
    }

    public void setProjections(List<Projection> projections) {
        this.projections = projections;
    }

    @ManyToMany
    public Set<Source> getSources() {
        return sources;
    }

    public void setSources(Set<Source> sources) {
        this.sources = sources;
    }

    @ManyToMany
    @OrderBy(value = "position ASC")
    public List<GroupByFragment> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupByFragment> groups) {
        this.groups = groups;
    }

    @ManyToMany
    public Set<Source> getJoins() {
        return joins;
    }

    public void setJoins(Set<Source> joins) {
        this.joins = joins;
    }

    @ManyToMany
    @OrderBy(value = "position ASC")
    public List<OrderByFragment> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderByFragment> orders) {
        this.orders = orders;
    }
    @ManyToOne
    public Query getParentQuery() {
        return parentQuery;
    }

    public void setParentQuery(Query parentQuery) {
        this.parentQuery = parentQuery;
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
