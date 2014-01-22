package pl.piotrsukiennik.tuner.utils;

import net.spy.memcached.MemcachedClient;

/**
 * Author: Piotr Sukiennik
 * Date: 28.07.13
 * Time: 13:14
 */
public class MemcachedHolder extends Holder {


    private MemcachedClient memcachedClient;

    public MemcachedClient getMemcachedClient() {
        if ( memcachedClient == null ) {
            memcachedClient = applicationContext.getBean( MemcachedClient.class );
        }
        return memcachedClient;
    }

    void setMemcachedClient( MemcachedClient memcachedClient ) {
        this.memcachedClient = memcachedClient;
    }

}
