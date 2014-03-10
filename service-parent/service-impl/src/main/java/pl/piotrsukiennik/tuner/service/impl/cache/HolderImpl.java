package pl.piotrsukiennik.tuner.service.impl.cache;

import java.util.Arrays;
import java.util.Collection;

/**
 * Author: Piotr Sukiennik
 * Date: 12.09.13
 * Time: 22:00
 */
public class HolderImpl<T> implements Holder<T> {

    private Collection<T> root;

    public HolderImpl( Collection<T> root ) {
        this.root = root;
    }

    @Override
    public Collection<T> get() {
        return root;
    }

    @Override
    public void add( T queryHash ) {
        root.add( queryHash );
    }

    @Override
    public void remove( T queryHash ) {
        root.remove( queryHash );
    }

    @Override
    public void removeAll() {
        root.clear();
    }

    @Override
    public void remove( Collection<T> objects ) {
        root.removeAll( objects );
    }

    @Override
    public void remove( T... objects ) {
        this.remove( Arrays.asList( objects ) );
    }
}
