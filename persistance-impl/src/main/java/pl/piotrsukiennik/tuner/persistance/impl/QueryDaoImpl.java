package pl.piotrsukiennik.tuner.persistance.impl;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.model.query.condition.Condition;
import pl.piotrsukiennik.tuner.model.query.other.ColumnValue;
import pl.piotrsukiennik.tuner.model.query.other.GroupByFragment;
import pl.piotrsukiennik.tuner.model.query.other.OrderByFragment;
import pl.piotrsukiennik.tuner.model.query.other.Values;
import pl.piotrsukiennik.tuner.model.query.projection.Projection;
import pl.piotrsukiennik.tuner.model.query.source.Source;
import pl.piotrsukiennik.tuner.persistance.QueryDao;

/**
 * Author: Piotr Sukiennik
 * Date: 12.08.13
 * Time: 15:31
 */
@Repository
@Transactional(value = "jsqlTunerTransactionManager")
class QueryDaoImpl extends CrudDaoImpl implements QueryDao {


    public <T extends Query> T getQueryByHash( String hash ) {
        return (T) s().createCriteria( Query.class ).add( Restrictions.eq( "hash", hash ) ).setMaxResults( 1 ).uniqueResult();
    }

    public <T extends Query> T getQueryByHash( Class<? extends Query> clazz, String hash ) {
        return (T) s().createCriteria( clazz ).add( Restrictions.eq( "hash", hash ) ).setMaxResults( 1 ).uniqueResult();
    }

    @Override
    public <T extends Query> T submit( T query ) {
        if ( query.getId() == 0 ) {
            T queryPersisted = getQueryByHash( query.getClass(), query.getHash() );
            if ( queryPersisted == null ) {
                return create( query );
            }
            if ( queryPersisted.getId() != 0 ) {
                return queryPersisted;
            }
            throw new RuntimeException( "Not persisted Query" );
        }
        else {
            return query;
        }
    }


    @Override
    public void submit( Condition condition ) {
        create( condition );
    }

    @Override
    public void submit( ColumnValue columnValue ) {
        create( columnValue );
    }

    @Override
    public void submit( GroupByFragment groupByFragment ) {
        create( groupByFragment );
    }

    @Override
    public void submit( OrderByFragment orderByFragment ) {
        create( orderByFragment );
    }

    @Override
    public void submit( Values values ) {
        create( values );
    }

    @Override
    public void submit( Projection projection ) {
        create( projection );
    }

    @Override
    public void submit( Source source ) {
        create( source );
    }

}

