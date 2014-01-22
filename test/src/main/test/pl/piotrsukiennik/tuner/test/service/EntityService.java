package pl.piotrsukiennik.tuner.test.service;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.piotrsukiennik.tuner.test.model.Test;

import java.util.List;

/**
 * Author: Piotr Sukiennik
 * Date: 28.07.13
 * Time: 12:58
 */
@Service
@Transactional
public class EntityService extends AbstractService {


    public Test testEntry() {
        Session session = s();
        return (Test) session.get( Test.class, 1 );
    }

    public List<Test> testEntities() {
        Session session = s();
        Criteria criteria = session.createCriteria( Test.class );
        return criteria.list();
    }
}
