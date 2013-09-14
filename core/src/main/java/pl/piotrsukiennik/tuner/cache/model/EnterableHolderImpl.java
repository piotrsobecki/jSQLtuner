package pl.piotrsukiennik.tuner.cache.model;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Author: Piotr Sukiennik
 * Date: 12.09.13
 * Time: 22:00
 */
public class EnterableHolderImpl<P,T> extends HolderImpl<T> implements EnterableHolder<P,T> {

    private Map<P,Holder<T>> paths = new LinkedHashMap<P,Holder<T>>();

    public EnterableHolderImpl(Collection<T> root) {
       super(root);
    }

    @Override
    public <X extends Holder<T>> X enter(P key) {
        return (X)paths.get(key);
    }

    @Override
    public <X extends Holder<T>> void put(P key, X value) {
        paths.put(key,value);

    }

}
