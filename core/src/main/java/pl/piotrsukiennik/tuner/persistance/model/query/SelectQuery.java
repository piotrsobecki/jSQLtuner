package pl.piotrsukiennik.tuner.persistance.model.query;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import pl.piotrsukiennik.tuner.persistance.model.query.condition.Condition;
import pl.piotrsukiennik.tuner.persistance.model.query.other.GroupByFragment;
import pl.piotrsukiennik.tuner.persistance.model.query.other.OrderByFragment;
import pl.piotrsukiennik.tuner.persistance.model.query.projection.Projection;
import pl.piotrsukiennik.tuner.persistance.model.query.source.Source;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 20:54
 */
@Entity
public class SelectQuery extends ReadQuery implements ConditionQuery, ProjectionsAware, JoinsAware{


    private Set<Projection> projections;


    private Set<Source> sources;

    private Set<Source> joins;

    private Set<GroupByFragment> groups;

    private Set<OrderByFragment> orders;

    private Set<Condition> conditions;

    private Query parentQuery;

    private Boolean distinctFragment;



    private int limitFrom;

    private int limitTo;


    public int getLimitFrom() {
        return limitFrom;
    }

    public void setLimitFrom(int limitFrom) {
        this.limitFrom = limitFrom;
    }

    public int getLimitTo() {
        return limitTo;
    }

    public void setLimitTo(int limitTo) {
        this.limitTo = limitTo;
    }

    public Boolean getDistinctFragment() {
        return distinctFragment;
    }

    public void setDistinctFragment(Boolean distinctFragment) {
        this.distinctFragment = distinctFragment;
    }


    @ManyToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    public Set<Projection> getProjections() {
        return projections;
    }

    public void setProjections(Set<Projection> projections) {
        this.projections = projections;
    }
    @JoinTable(name = "SelectQuery_Joins")
    @ManyToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    public Set<Source> getSources() {
        return sources;
    }

    public void setSources(Set<Source> sources) {
        this.sources = sources;
    }
    @JoinTable(name = "SelectQuery_Sources")
    @ManyToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    public Set<Source> getJoins() {
        return joins;
    }

    public void setJoins(Set<Source> joins) {
        this.joins = joins;
    }
    @ManyToMany(cascade = CascadeType.ALL)
    @OrderBy(value = "position ASC")
    @LazyCollection(LazyCollectionOption.FALSE)
    public Set<GroupByFragment> getGroups() {
        return groups;
    }

    public void setGroups(Set<GroupByFragment> groups) {
        this.groups = groups;
    }




    @ManyToMany(cascade = CascadeType.ALL)
    @OrderBy(value = "position ASC")
    @LazyCollection(LazyCollectionOption.FALSE)
    public Set<OrderByFragment> getOrders() {
        return orders;
    }

    public void setOrders(Set<OrderByFragment> orders) {
        this.orders = orders;
    }
    @ManyToOne(cascade = CascadeType.ALL,optional = true)
    public Query getParentQuery() {
        return parentQuery;
    }

    public void setParentQuery(Query parentQuery) {
        this.parentQuery = parentQuery;
    }

    @Override
    @ManyToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    public Set<Condition> getConditions() {
        return conditions;
    }

    @Override
    public void setConditions(Set<Condition> conditions) {
        this.conditions = conditions;
    }
}
