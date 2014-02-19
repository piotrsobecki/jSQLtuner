package pl.piotrsukiennik.tuner.persistance.impl;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Repository;
import pl.piotrsukiennik.tuner.persistance.*;

/**
 * Author: Piotr Sukiennik
 * Date: 28.07.13
 * Time: 13:14
 */
@Repository
class DaoImpl extends Dao implements ApplicationListener<ContextRefreshedEvent> {

    protected DaoImpl() {
    }

    @Override
    public void onApplicationEvent( ContextRefreshedEvent contextRefreshedEvent ) {
        ApplicationContext applicationContext = contextRefreshedEvent.getApplicationContext();
        setLog( applicationContext.getBean( LogDao.class ) );
        setQuery( applicationContext.getBean( QueryDao.class ) );
        setSchema( applicationContext.getBean( SchemaDao.class ) );
        setCommon( applicationContext.getBean( CommonDao.class ) );
    }


}
