package pl.piotrsukiennik.tuner.tree;

import java.util.Collection;

/**
 * Author: Piotr Sukiennik
 * Date: 12.09.13
 * Time: 21:37
 */
public interface Leaf<T> {
    Collection<T> get();

    void add( T queryHash );

    void remove( T object );

    void removeAll();

    void remove( T... objects );

    void remove( Collection<T> objects );

}
