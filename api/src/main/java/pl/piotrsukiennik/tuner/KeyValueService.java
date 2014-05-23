package pl.piotrsukiennik.tuner;

import java.util.concurrent.TimeUnit;

/**
 * Author: Piotr Sukiennik
 * Date: 04.07.13
 * Time: 17:49
 */
public interface KeyValueService{

    String getIdentifier();

    Object get( String key );

    void put( String key, Object value );

    void put( String key, Object value, TimeUnit timeUnit, long expiration );

    void delete( String key );
}
