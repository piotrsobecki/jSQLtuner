package pl.piotrsukiennik.tuner.test.service;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.piotrsukiennik.tuner.test.model.TestEntity;

import java.util.List;

/**
 * Author: Piotr Sukiennik
 * Date: 28.07.13
 * Time: 12:58
 */
@Service
@Transactional("transactionManager")
public class EntityService extends AbstractService {


    public void deleteTestEntry( TestEntity testEntity ) {
        Session session = s();
        session.delete( testEntity );
        session.flush();
    }

    public TestEntity saveTestEntry( String value ) {
        TestEntity test = new TestEntity();
        test.setString( value );
        Session session = s();
        Integer id = (Integer) session.save( test );
        session.flush();
        test.setId( id );
        return test;
    }

    public List<TestEntity> getTestEntries( String value ) {
        TestEntity test = new TestEntity();
        test.setString( value );
        Session session = s();
        return session.createCriteria( TestEntity.class ).add( Restrictions.eq( "string", value ) ).list();
    }

    public TestEntity getTestEntry( Integer id ) {
        Session session = s();
        return (TestEntity) session.get( TestEntity.class, id );
    }

    public List<TestEntity> getTestEntities() {
        Session session = s();
        Criteria criteria = session.createCriteria( TestEntity.class );
        return criteria.list();
    }
}
