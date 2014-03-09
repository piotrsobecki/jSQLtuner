package pl.piotrsukiennik.tuner.persistence.impl;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import pl.piotrsukiennik.tuner.model.expression.Expression;
import pl.piotrsukiennik.tuner.model.expression.OperatorExpression;
import pl.piotrsukiennik.tuner.model.expression.projection.Projection;
import pl.piotrsukiennik.tuner.model.other.*;
import pl.piotrsukiennik.tuner.model.source.Source;
import pl.piotrsukiennik.tuner.persistence.QueryElementDao;

/**
 * Author: Piotr Sukiennik
 * Date: 12.08.13
 * Time: 15:31
 */
@Repository
class QueryElementDaoImpl extends CrudDaoImpl implements QueryElementDao {


    @Override
    public GroupByFragment getOrCreateGroupBy( GroupByFragment groupByFragment ) {
        GroupByFragment groupByFragmentPer = (GroupByFragment) s().createCriteria( GroupByFragment.class )
         .add( Restrictions.eq( "expression", groupByFragment.getExpression() ) )
         .add( Restrictions.eq( "position", groupByFragment.getPosition() ) ).setMaxResults( 1 ).uniqueResult();
        if ( groupByFragmentPer == null ) {
            return create( groupByFragment );
        }
        return groupByFragmentPer;
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

