package pl.piotrsukiennik.tuner.persistence.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.annotation.Resource;

/**
 * @author Piotr Sukiennik
 * @date 14.01.14
 */
public abstract class AbstractDaoImpl {

    @Resource(name = "jsqlTunerSessionFactory")
    private SessionFactory sessionFactory;


    protected Session s() {
        return sessionFactory.getCurrentSession();
    }

}
