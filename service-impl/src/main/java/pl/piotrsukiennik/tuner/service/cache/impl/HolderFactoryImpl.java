package pl.piotrsukiennik.tuner.service.cache.impl;

import pl.piotrsukiennik.tuner.service.cache.Holder;
import pl.piotrsukiennik.tuner.service.cache.HolderFactory;
import pl.piotrsukiennik.tuner.util.Objects2;

import java.util.Collection;

/**
 * Author: Piotr Sukiennik
 * Date: 12.09.13
 * Time: 22:42
 */
public class HolderFactoryImpl<T extends Collection> implements HolderFactory<T> {
    protected Class<T> holerCollection;

    public HolderFactoryImpl( Class<T> holerCollection ) {
        this.holerCollection = holerCollection;
    }

    @Override
    public <Q> Holder<Q> build() {
        return new HolderImpl( Objects2.newInstance( holerCollection ) );
    }
}
