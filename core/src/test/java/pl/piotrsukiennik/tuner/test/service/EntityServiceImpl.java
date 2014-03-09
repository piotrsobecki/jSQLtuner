package pl.piotrsukiennik.tuner.test.service;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.piotrsukiennik.tuner.test.model.MockData;
import pl.piotrsukiennik.tuner.test.model.MockDataModel;

import java.util.List;

/**
 * Author: Piotr Sukiennik
 * Date: 28.07.13
 * Time: 12:58
 */
@Service
@Transactional("transactionManager")
public class EntityServiceImpl extends AbstractService implements EntityService {


    @Override
    public void deleteTestEntry( MockDataModel mockData ) {
        Session session = s();
        session.delete( mockData );
        session.flush();
    }

    @Override
    public MockDataModel save( MockData mockData ) {
        MockDataModel mockDataModel = new MockDataModel( mockData );
        Session session = s();
        Integer id = (Integer) session.save( mockDataModel );
        mockDataModel.setId( id );
        session.flush();
        return mockDataModel;
    }

    @Override
    public MockDataModel save( String email ) {
        MockDataModel test = new MockDataModel();
        test.setEmail( email );
        Session session = s();
        Integer id = (Integer) session.save( test );
        session.flush();
        test.setId( id );
        return test;
    }

    @Override
    public List<MockDataModel> getEntriesByEmail( String email ) {
        MockDataModel test = new MockDataModel();
        test.setEmail( email );
        Session session = s();
        return session.createCriteria( MockDataModel.class ).add( Restrictions.eq( "email", email ) ).list();
    }

    @Override
    public MockDataModel getTestEntry( Integer id ) {
        Session session = s();
        return (MockDataModel) session.get( MockDataModel.class, id );
    }

    @Override
    public List<MockDataModel> getTestEntities() {
        Session session = s();
        Criteria criteria = session.createCriteria( MockDataModel.class );
        return criteria.list();
    }
}
