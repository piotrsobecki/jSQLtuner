package pl.piotrsukiennik.tuner.querytree;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Author: Piotr Sukiennik
 * Date: 12.09.13
 * Time: 22:00
 */
public class BranchImpl<P, T> extends LeafImpl<T> implements Branch<P, T> {

    private Map<P, Leaf<T>> paths = new LinkedHashMap<P, Leaf<T>>();

    public BranchImpl( Collection<T> root ) {
        super( root );
    }

    @Override
    public <X extends Leaf<T>> X enter( P key ) {
        return (X) paths.get( key );
    }

    @Override
    public <X extends Leaf<T>> void put( P key, X value ) {
        paths.put( key, value );

    }

    @Override
    public void remove( T queryHash ) {
        super.remove( queryHash );
        for ( Map.Entry<P, Leaf<T>> holder : paths.entrySet() ) {
            holder.getValue().remove( queryHash );
        }
    }

    @Override
    public void removeAll() {
        super.removeAll();
        for ( Map.Entry<P, Leaf<T>> holder : paths.entrySet() ) {
            holder.getValue().removeAll();
        }
    }

    @Override
    public void remove( T... objects ) {
        super.remove( objects );
        for ( Map.Entry<P, Leaf<T>> holder : paths.entrySet() ) {
            holder.getValue().remove( objects );
        }
    }
}
