package pl.piotrsukiennik.tuner.service.cache.model;

import pl.piotrsukiennik.tuner.service.util.Objects2;

import java.util.Collection;

/**
 * Author: Piotr Sukiennik
 * Date: 12.09.13
 * Time: 22:42
 */
public class HolderFactory<T extends Collection> {
    protected Class<T> holerCollection;

    public HolderFactory( Class<T> holerCollection ) {
        this.holerCollection = holerCollection;
    }

    public <Q> Holder<Q> build() {
        return new HolderImpl( Objects2.newInstance( holerCollection ) );
    }
}
