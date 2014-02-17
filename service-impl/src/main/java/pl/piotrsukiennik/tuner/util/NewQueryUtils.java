package pl.piotrsukiennik.tuner.util;

import pl.piotrsukiennik.tuner.model.query.JoinsAware;
import pl.piotrsukiennik.tuner.model.query.ProjectionsAware;
import pl.piotrsukiennik.tuner.model.query.SelectQuery;
import pl.piotrsukiennik.tuner.model.query.SourcesAware;
import pl.piotrsukiennik.tuner.model.query.other.JoinFragment;
import pl.piotrsukiennik.tuner.model.query.other.OrderByFragment;
import pl.piotrsukiennik.tuner.model.query.projection.ColumnProjection;
import pl.piotrsukiennik.tuner.model.query.projection.Projection;
import pl.piotrsukiennik.tuner.model.query.source.Source;
import pl.piotrsukiennik.tuner.model.schema.Table;
import pl.piotrsukiennik.tuner.service.QueryContext;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Author: Piotr Sukiennik
 * Date: 28.07.13
 * Time: 19:43
 */
public class NewQueryUtils {

    private NewQueryUtils() {

    }

    public static Table map( QueryContext queryContext, net.sf.jsqlparser.schema.Table table ) {
        return queryContext.getTable( table.getWholeTableName() );
    }

    public static void addProjection( ProjectionsAware query, Projection projection ) {
        Set<Projection> projections = query.getProjections();
        if ( projections == null ) {
            projections = new LinkedHashSet<Projection>();
            query.setProjections( projections );
        }
        projection.setPosition( projections.size() );
        projections.add( projection );
    }


    public static void addSource( SourcesAware query, Source source ) {
        Set<Source> sources = query.getSources();
        if ( sources == null ) {
            sources = new LinkedHashSet<Source>();
            query.setSources( sources );
        }
        sources.add( source );
    }


    public static void addJoin( JoinsAware query, JoinFragment source ) {
        Set<JoinFragment> sources = query.getJoins();
        if ( sources == null ) {
            sources = new LinkedHashSet<JoinFragment>();
            query.setJoins( sources );
        }
        sources.add( source );

    }

    public static void addOrderByFragment( SelectQuery selectQuery, OrderByFragment orderByFragment ) {
        Set<OrderByFragment> sources = selectQuery.getOrders();
        if ( sources == null ) {
            sources = new LinkedHashSet<OrderByFragment>();
            selectQuery.setOrders( sources );
        }
        orderByFragment.setPosition( sources.size() );
        sources.add( orderByFragment );
    }

    public static void addGroupByFragment( SelectQuery selectQuery, ColumnProjection groupByFragment ) {
        Set<ColumnProjection> sources = selectQuery.getGroups();
        if ( sources == null ) {
            sources = new LinkedHashSet<ColumnProjection>();
            selectQuery.setGroups( sources );
        }
        groupByFragment.setPosition( sources.size() );
        sources.add( groupByFragment );
    }
}
