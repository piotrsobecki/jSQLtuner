package pl.piotrsukiennik.tuner.util.holder;

import net.spy.memcached.MemcachedClient;
import org.springframework.stereotype.Component;
import pl.piotrsukiennik.tuner.persistance.service.ILogService;
import pl.piotrsukiennik.tuner.persistance.service.IQueryService;
import pl.piotrsukiennik.tuner.persistance.service.ISchemaService;

/**
 * Author: Piotr Sukiennik
 * Date: 28.07.13
 * Time: 13:14
 */
@Component
public class MemcachedHolder extends Holder {


    private MemcachedClient memcachedClient;


    public MemcachedClient getMemcachedClient() {
        if (memcachedClient==null){
            memcachedClient = applicationContext.getBean(MemcachedClient.class);
        }
        return memcachedClient;
    }

    void setMemcachedClient(MemcachedClient memcachedClient) {
        this.memcachedClient = memcachedClient;
    }

}
