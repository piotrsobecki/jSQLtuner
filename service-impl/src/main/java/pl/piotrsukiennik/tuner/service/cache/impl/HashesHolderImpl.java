package pl.piotrsukiennik.tuner.service.cache.impl;

import org.springframework.stereotype.Service;
import pl.piotrsukiennik.tuner.service.cache.HashesHolder;
import pl.piotrsukiennik.tuner.service.cache.Holder;
import pl.piotrsukiennik.tuner.service.cache.HolderEnterable;
import pl.piotrsukiennik.tuner.service.cache.HolderEnterableFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;

/**
 * Author: Piotr Sukiennik
 * Date: 12.09.13
 * Time: 21:39
 */
@Service
public class HashesHolderImpl<P, K> implements HashesHolder<P, K> {
    private HolderEnterableFactory holderEnterableFactory;

    private HolderEnterable<P, K> rootHolder;

    public HashesHolderImpl() {
        this( new HolderEnterableFactoryImpl( LinkedHashSet.class ) );
    }

    public HashesHolderImpl( HolderEnterableFactory holderEnterableFactory ) {
        this.rootHolder = holderEnterableFactory.buildEnterable();
        this.holderEnterableFactory = holderEnterableFactory;
    }

    @Override
    public void put( P[] path, K hash ) {
        Holder holder = rootHolder;
        for ( int i = 0; i < path.length; i++ ) {
            if ( holder instanceof HolderEnterable ) {
                Holder holderNew = ( (HolderEnterable<P, K>) holder ).enter( path[i] );
                if ( holderNew == null ) {
                    holderNew = holderEnterableFactory.buildEnterable();
                    ( (HolderEnterable) holder ).put( path[i], holderNew );
                }
                holder = holderNew;
            }
            holder.add( hash );
        }
    }

    @Override
    public Collection<K> get( P[] path ) {
        Holder holder = rootHolder;
        for ( P str : path ) {
            if ( holder instanceof HolderEnterable ) {
                holder = ( (HolderEnterable<P, K>) holder ).enter( str );
            }
        }
        return holder.get();
    }

    @Override
    public Collection<K> getRemove( P[] path ) {
        Holder holder = rootHolder;
        for ( P str : path ) {
            if ( holder != null && holder instanceof HolderEnterable ) {
                holder = ( (HolderEnterable<P, K>) holder ).enter( str );
            }
        }
        if ( holder != null ) {
            Collection<K> objects = holder.get();
            rootHolder.remove( objects );
            return objects;
        }
        return Collections.EMPTY_LIST;
    }


}
