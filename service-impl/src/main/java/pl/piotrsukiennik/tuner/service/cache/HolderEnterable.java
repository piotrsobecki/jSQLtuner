package pl.piotrsukiennik.tuner.service.cache;

/**
 * Author: Piotr Sukiennik
 * Date: 12.09.13
 * Time: 21:37
 */
public interface HolderEnterable<P, T> extends Holder<T> {
    <X extends Holder<T>> X enter( P key );

    <X extends Holder<T>> void put( P key, X value );
}
