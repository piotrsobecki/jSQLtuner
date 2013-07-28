package pl.piotrsukiennik.tuner.persistance.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.inject.Named;

/**
 * Author: Piotr Sukiennik
 * Date: 27.07.13
 * Time: 15:00
 */
public abstract class AbstractService {
    private @Resource(name="jsqlTunerSessionFactory") SessionFactory sessionFactory;

    protected Session s(){
        return sessionFactory.getCurrentSession();
    }


   /* @PostConstruct
    protected void attachSessionFactory(ApplicationContext applicationContext){
        this.sessionFactory=applicationContext.getBean("jsqlTunerSessionFactory",SessionFactory.class);
    }*/
}
