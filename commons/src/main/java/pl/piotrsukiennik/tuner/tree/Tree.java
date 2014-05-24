package pl.piotrsukiennik.tuner.tree;

import java.util.Collection;

/**
 * @author Piotr Sukiennik
 * @date 16.02.14
 */
public interface Tree<P, K> {
    void put( P[] path, K hash );

    Collection<K> get( P[] path );

    Collection<K> getRemove( P[] path );
}
