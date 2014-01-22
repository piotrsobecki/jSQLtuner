package pl.piotrsukiennik.tuner.model.query;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import pl.piotrsukiennik.tuner.model.query.condition.Condition;
import pl.piotrsukiennik.tuner.model.query.other.JoinFragment;
import pl.piotrsukiennik.tuner.model.query.other.OrderByFragment;
import pl.piotrsukiennik.tuner.model.query.projection.ColumnProjection;
import pl.piotrsukiennik.tuner.model.query.projection.Projection;
import pl.piotrsukiennik.tuner.model.query.source.Source;
import pl.piotrsukiennik.tuner.model.schema.Table;

import javax.persistence.*;
import java.util.Set;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 20:54
 */
@Entity
public class SelectQuery extends ReadQuery implements ConditionQuery, ProjectionsAware, JoinsAware {


    private Set<Projection> projections;

    private Set<Source> sources;

    private Set<JoinFragment> joins;

    private Set<ColumnProjection> groups;

    private Set<OrderByFragment> orders;

    private Query parentQuery;

    private boolean distinctFragment;

    private Condition whereCondition;

    private Condition havingCondition;

    private Table intoTable;

    private long limitFrom;

    private long limitTo;


    public long getLimitFrom() {
        return limitFrom;
    }

    public void setLimitFrom( long limitFrom ) {
        this.limitFrom = limitFrom;
    }

    public long getLimitTo() {
        return limitTo;
    }

    public void setLimitTo( long limitTo ) {
        this.limitTo = limitTo;
    }

    public boolean getDistinctFragment() {
        return distinctFragment;
    }

    public void setDistinctFragment( boolean distinctFragment ) {
        this.distinctFragment = distinctFragment;
    }


    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @LazyCollection(LazyCollectionOption.FALSE)
    public Set<Projection> getProjections() {
        return projections;
    }

    public void setProjections( Set<Projection> projections ) {
        this.projections = projections;
    }

    @JoinTable(name = "SelectQuery_Joins")
    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @LazyCollection(LazyCollectionOption.FALSE)
    public Set<Source> getSources() {
        return sources;
    }

    public void setSources( Set<Source> sources ) {
        this.sources = sources;
    }

    @JoinTable(name = "SelectQuery_Sources")
    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @LazyCollection(LazyCollectionOption.FALSE)
    public Set<JoinFragment> getJoins() {
        return joins;
    }

    public void setJoins( Set<JoinFragment> joins ) {
        this.joins = joins;
    }

    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @OrderBy(value = "position ASC")
    @LazyCollection(LazyCollectionOption.FALSE)
    public Set<ColumnProjection> getGroups() {
        return groups;
    }

    public void setGroups( Set<ColumnProjection> groups ) {
        this.groups = groups;
    }

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @OrderBy(value = "position ASC")
    @LazyCollection(LazyCollectionOption.FALSE)
    public Set<OrderByFragment> getOrders() {
        return orders;
    }

    public void setOrders( Set<OrderByFragment> orders ) {
        this.orders = orders;
    }

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, optional = true)
    public Query getParentQuery() {
        return parentQuery;
    }

    public void setParentQuery( Query parentQuery ) {
        this.parentQuery = parentQuery;
    }


    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    public Table getIntoTable() {
        return intoTable;
    }

    public void setIntoTable( Table intoTable ) {
        this.intoTable = intoTable;
    }

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    public Condition getWhereCondition() {
        return whereCondition;
    }

    public void setWhereCondition( Condition whereCondition ) {
        this.whereCondition = whereCondition;
    }

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    public Condition getHavingCondition() {
        return havingCondition;
    }

    public void setHavingCondition( Condition havingCondition ) {
        this.havingCondition = havingCondition;
    }
}