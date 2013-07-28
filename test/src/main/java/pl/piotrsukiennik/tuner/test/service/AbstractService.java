package pl.piotrsukiennik.tuner.test.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.annotation.Resource;
import javax.inject.Named;

/**
 * Author: Piotr Sukiennik
 * Date: 28.07.13
 * Time: 12:58
 */
public abstract class AbstractService {
    private @Resource
    @Named("sessionFactory")
    SessionFactory sessionFactory;

    public Session s(){
        return sessionFactory.getCurrentSession();
    }
}
