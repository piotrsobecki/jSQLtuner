package pl.piotrsukiennik.tuner.cache.model;

import java.util.Collection;
import java.util.List;

/**
 * Author: Piotr Sukiennik
 * Date: 12.09.13
 * Time: 22:00
 */
public class HolderImpl<T> implements Holder<T> {

    private Collection<T> root;

    public HolderImpl(Collection<T> root) {
        this.root = root;
    }

    @Override
    public Collection<T> get() {
        return root;
    }

    @Override
    public void add(T queryHash) {
        root.add(queryHash);
    }

    @Override
    public void remove(T queryHash) {
        root.remove(queryHash);
    }

    @Override
    public void removeAll() {
        root.clear();
    }

}
