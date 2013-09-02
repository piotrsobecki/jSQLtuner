package pl.piotrsukiennik.tuner.datasources.keyvalue.memcached;

import net.spy.memcached.MemcachedClient;
import org.springframework.stereotype.Component;
import pl.piotrsukiennik.tuner.datasources.keyvalue.IKeyValueService;
import pl.piotrsukiennik.tuner.util.holder.MemcachedHolder;
import pl.piotrsukiennik.tuner.util.holder.ServicesHolder;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.inject.Inject;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * Author: Piotr Sukiennik
 * Date: 04.07.13
 * Time: 17:51
 */
@Component(value = "KeyValueMemcachedService")
public class KeyValueMemcachedService implements IKeyValueService{
    private static final long MAX_EXPIRATION = TimeUnit.DAYS.toSeconds(30);

    private  MemcachedClient memcachedClient;

    public KeyValueMemcachedService() {
    }

    public KeyValueMemcachedService( MemcachedClient memcachedClient) {
        this.memcachedClient = memcachedClient;
    }


    @Resource
    public void init(MemcachedHolder servicesHolder){
        this.memcachedClient = servicesHolder.getMemcachedClient();
    }


    @Override
    public void put(String key, Object value) {
        put(key,value,TimeUnit.SECONDS,MAX_EXPIRATION);
    }

    @Override
    public Object get(String key) {
        return memcachedClient.get(key);
    }

    @Override
    public void put(String key, Object value, TimeUnit timeUnit, long expiration) {
        memcachedClient.set(key,(int)(timeUnit.toSeconds(expiration)),value);
    }

    @Override
    public void delete(String key) {
      memcachedClient.delete(key);
    }

    @Override
    public String getIdentifier() {
        String id = "";
        String comma ="";
        for (SocketAddress socketAddress: memcachedClient.getAvailableServers()){
            id+=comma+socketAddress.toString();
            comma=",";
        }
        return id;
    }
}
