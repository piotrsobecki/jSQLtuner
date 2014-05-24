package pl.piotrsukiennik.tuner.tree;

/**
 * Author: Piotr Sukiennik
 * Date: 12.09.13
 * Time: 21:37
 */
public interface Branch<P, T> extends Leaf<T> {
    <X extends Leaf<T>> X enter( P key );

    <X extends Leaf<T>> void put( P key, X value );
}
