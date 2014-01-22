package pl.piotrsukiennik.tuner.persistance.transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author Piotr Sukiennik
 * @date 14.01.14
 */
@Transactional(value = "jsqlTunerTransactionManager")
public abstract class AbstractDaoImpl {

    @Resource( name = "jsqlTunerSessionFactory" )
    private SessionFactory sessionFactory;


    protected Session s() {
        return sessionFactory.getCurrentSession();
    }

}
