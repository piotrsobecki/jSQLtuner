package pl.piotrsukiennik.tuner.datasources.keyvalue.memcached;

import net.spy.memcached.MemcachedClient;
import pl.piotrsukiennik.tuner.datasources.keyvalue.IKeyValueService;

import java.util.concurrent.TimeUnit;

/**
 * Author: Piotr Sukiennik
 * Date: 04.07.13
 * Time: 17:51
 */
public class KeyValueMemcachedService implements IKeyValueService{
    private static final long MAX_EXPIRATION = TimeUnit.DAYS.toSeconds(30);

    private MemcachedClient memcachedClient;

    public KeyValueMemcachedService(MemcachedClient memcachedClient) {
        this.memcachedClient = memcachedClient;
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
}
