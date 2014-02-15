package pl.piotrsukiennik.tuner.persistance;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Repository;

/**
 * Author: Piotr Sukiennik
 * Date: 28.07.13
 * Time: 13:14
 */
@Repository
class DaoHolderImpl extends DaoHolder implements ApplicationListener<ContextRefreshedEvent> {


    @Override
    public void onApplicationEvent( ContextRefreshedEvent contextRefreshedEvent ) {
        ApplicationContext applicationContext = contextRefreshedEvent.getApplicationContext();
        setLogDao( applicationContext.getBean( LogDao.class ) );
        setQueryDao( applicationContext.getBean( QueryDao.class ) );
        setSchemaDao( applicationContext.getBean( SchemaDao.class ) );
        setCommonDao( applicationContext.getBean( CommonDao.class ) );
    }


}
