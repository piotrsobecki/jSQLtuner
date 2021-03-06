package pl.piotrsukiennik.tuner.persistence.impl;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Repository;
import pl.piotrsukiennik.tuner.persistence.*;

/**
 * Author: Piotr Sukiennik
 * Date: 28.07.13
 * Time: 13:14
 */
@Repository
class DaoImpl extends Dao implements ApplicationContextAware {

    protected DaoImpl() {
    }


    public void setApplicationContext( ApplicationContext applicationContext ) throws BeansException {
        setLog( applicationContext.getBean( LogDao.class ) );
        setQuery( applicationContext.getBean( QueryDao.class ) );
        setSchema( applicationContext.getBean( SchemaDao.class ) );
        setQueryElement( applicationContext.getBean( QueryElementDao.class ) );
    }
}
