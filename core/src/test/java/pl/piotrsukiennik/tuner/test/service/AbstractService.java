package pl.piotrsukiennik.tuner.test.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author: Piotr Sukiennik
 * Date: 28.07.13
 * Time: 12:58
 */

public abstract class AbstractService {
    private
    @Autowired
    SessionFactory sessionFactory;

    public Session s() {
        return sessionFactory.getCurrentSession();
    }
}
