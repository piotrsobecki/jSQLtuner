package pl.piotrsukiennik.tuner.persistance.impl;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import pl.piotrsukiennik.tuner.model.expression.Expression;
import pl.piotrsukiennik.tuner.model.expression.OperatorExpression;
import pl.piotrsukiennik.tuner.model.expression.projection.Projection;
import pl.piotrsukiennik.tuner.model.other.*;
import pl.piotrsukiennik.tuner.model.source.Source;
import pl.piotrsukiennik.tuner.persistance.QueryElementDao;

/**
 * Author: Piotr Sukiennik
 * Date: 12.08.13
 * Time: 15:31
 */
@Repository
class QueryElementDaoImpl extends CrudDaoImpl implements QueryElementDao {


    @Override
    public GroupByFragment getOrCreateGroupBy( Expression expression, int position ) {
        GroupByFragment groupByFragment = (GroupByFragment) s().createCriteria( GroupByFragment.class )
         .add( Restrictions.eq( "expression", expression ) )
         .add( Restrictions.eq( "position", position ) ).setMaxResults( 1 ).uniqueResult();
        if ( groupByFragment == null ) {
            groupByFragment = new GroupByFragment();
            groupByFragment.setExpression( expression );
            groupByFragment.setPosition( position );
            return create( groupByFragment );
        }
        return groupByFragment;
    }

    @Override
    public OperatorExpression getOrCreate( OperatorExpression expression ) {
        return create( expression );
    }

    @Override
    public ColumnValue getOrCreate( ColumnValue columnValue ) {
        return create( columnValue );
    }

    @Override
    public GroupByFragment getOrCreateGroupBy( GroupByFragment groupByFragment ) {
        return create( groupByFragment );
    }

    @Override
    public OrderByFragment getOrCreate( OrderByFragment orderByFragment ) {
        return create( orderByFragment );
    }

    @Override
    public JoinFragment getOrCreate( JoinFragment joinFragment ) {
        return create( joinFragment );
    }

    @Override
    public Values getOrCreate( Values values ) {
        return create( values );
    }

    @Override
    public <T extends Projection> T getOrCreate( T projection ) {
        return create( projection );
    }

    @Override
    public <T extends Source> T getOrCreate( T source ) {
        return create( source );
    }

    @Override
    public <E extends Expression> E getOrCreate( E expression ) {
        return create( expression );
    }
}

