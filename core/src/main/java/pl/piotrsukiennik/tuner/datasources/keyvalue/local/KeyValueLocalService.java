package pl.piotrsukiennik.tuner.datasources.keyvalue.local;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import net.spy.memcached.MemcachedClient;
import org.springframework.stereotype.Component;
import pl.piotrsukiennik.tuner.datasources.keyvalue.IKeyValueService;

import javax.annotation.Resource;
import java.net.SocketAddress;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Author: Piotr Sukiennik
 * Date: 04.07.13
 * Time: 17:51
 */
@Component(value = "KeyValueLocalService")
public class KeyValueLocalService implements IKeyValueService{
    private Cache<Object,Object> cache= CacheBuilder.newBuilder().
            expireAfterAccess(30, TimeUnit.DAYS).
            maximumSize(1000).
            build();

    public KeyValueLocalService() {
    }


    @Override
    public void put(String key, Object value) {
        cache.put(key, value);
    }

    @Override
    public Object get(String key) {
        return cache.getIfPresent(key);
    }

    @Override
    public void put(String key, Object value, TimeUnit timeUnit, long expiration) {
        cache.put(key, value);
    }

    @Override
    public void delete(String key) {
        cache.invalidate(key);
    }

    @Override
    public String getIdentifier() {
        return "KeyValueLocalService";
    }
}
