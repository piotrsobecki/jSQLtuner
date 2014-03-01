package pl.piotrsukiennik.tuner.persistance.impl;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import pl.piotrsukiennik.tuner.model.log.WriteQueryExecution;
import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.persistance.QueryDao;

/**
 * Author: Piotr Sukiennik
 * Date: 12.08.13
 * Time: 15:31
 */
@Repository
class QueryDaoImpl extends CrudDaoImpl implements QueryDao {

    public <T extends Query> T getQuery( String hash ) {
        return (T) s().createCriteria( Query.class ).add( Restrictions.eq( "hash", hash ) ).setMaxResults( 1 ).uniqueResult();
    }

    public <T extends Query> T getQuery( Class<? extends Query> clazz, String hash ) {
        return (T) s().createCriteria( clazz ).add( Restrictions.eq( "hash", hash ) ).setMaxResults( 1 ).uniqueResult();
    }

    @Override
    public <T extends Query> T createQuery( T query ) {
        if ( query.getId() == 0 ) {
            T queryPersisted = getQuery( query.getClass(), query.getHash() );
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
    public void createQueryExecution( WriteQueryExecution writeQueryExecution ) {
        create( writeQueryExecution );
    }
}

