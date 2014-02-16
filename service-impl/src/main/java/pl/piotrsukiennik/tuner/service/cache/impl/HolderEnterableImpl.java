package pl.piotrsukiennik.tuner.service.cache.impl;

import pl.piotrsukiennik.tuner.service.cache.Holder;
import pl.piotrsukiennik.tuner.service.cache.HolderEnterable;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Author: Piotr Sukiennik
 * Date: 12.09.13
 * Time: 22:00
 */
public class HolderEnterableImpl<P, T> extends HolderImpl<T> implements HolderEnterable<P, T> {

    private Map<P, Holder<T>> paths = new LinkedHashMap<P, Holder<T>>();

    public HolderEnterableImpl( Collection<T> root ) {
        super( root );
    }

    @Override
    public <X extends Holder<T>> X enter( P key ) {
        return (X) paths.get( key );
    }

    @Override
    public <X extends Holder<T>> void put( P key, X value ) {
        paths.put( key, value );

    }

    @Override
    public void remove( T queryHash ) {
        super.remove( queryHash );
        for ( Map.Entry<P, Holder<T>> holder : paths.entrySet() ) {
            holder.getValue().remove( queryHash );
        }
    }

    @Override
    public void removeAll() {
        super.removeAll();
        for ( Map.Entry<P, Holder<T>> holder : paths.entrySet() ) {
            holder.getValue().removeAll();
        }
    }

    @Override
    public void remove( T... objects ) {
        super.remove( objects );
        for ( Map.Entry<P, Holder<T>> holder : paths.entrySet() ) {
            holder.getValue().remove( objects );
        }
    }
}
