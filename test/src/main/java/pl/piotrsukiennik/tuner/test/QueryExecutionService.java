package pl.piotrsukiennik.tuner.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Author: Piotr Sukiennik
 * Date: 08.07.13
 * Time: 17:49
 */
@Service
@Transactional
public class QueryExecutionService {

    private @Resource SessionFactory sessionFactory;


    public Session s(){
        return sessionFactory.getCurrentSession();
    }

    public List execute(String query){
       return s().createSQLQuery(query).list();
    }
}
