package pl.piotrsukiennik.tuner.dataservice;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import pl.piotrsukiennik.tuner.service.KeyValueService;

import java.util.concurrent.TimeUnit;

/**
 * Author: Piotr Sukiennik
 * Date: 04.07.13
 * Time: 17:51
 */
public class KeyValueServiceLocalImpl implements KeyValueService {
    private Cache<String, Object> cache = CacheBuilder.newBuilder().
     expireAfterAccess( 5, TimeUnit.MINUTES ).
     maximumSize( 1000 ).
     build();

    public KeyValueServiceLocalImpl() {
    }


    @Override
    public void put( String key, Object value ) {
        cache.put( key, value );
    }

    @Override
    public Object get( String key ) {
        return cache.getIfPresent( key );
    }

    @Override
    public void put( String key, Object value, TimeUnit timeUnit, long expiration ) {
        cache.put( key, value );
    }

    @Override
    public void delete( String key ) {
        cache.invalidate( key );
    }

    @Override
    public String getIdentifier() {
        return "KeyValueServiceLocal";
    }
}
