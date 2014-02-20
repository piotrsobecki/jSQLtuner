package pl.piotrsukiennik.tuner.test.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Author: Piotr Sukiennik
 * Date: 28.07.13
 * Time: 12:58
 */
@Transactional
public abstract class AbstractService {
    private
    @Resource
    SessionFactory sessionFactory;

    public Session s() {
        return sessionFactory.getCurrentSession();
    }
}
