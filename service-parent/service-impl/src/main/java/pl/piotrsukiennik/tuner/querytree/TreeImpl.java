package pl.piotrsukiennik.tuner.querytree;

import org.springframework.stereotype.Service;
import pl.piotrsukiennik.tuner.util.GenericBuilder;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;

/**
 * Author: Piotr Sukiennik
 * Date: 12.09.13
 * Time: 21:39
 */
@Service
public class TreeImpl<P, K> implements Tree<P, K> {

    private GenericBuilder<Branch<P, K>> branchBuilder = new GenericBuilder<Branch<P, K>>() {
        @Override
        public Branch<P, K> build() {
            return new BranchImpl<P,K>( new LinkedHashSet<K>( ) );
        }
    };

    private Branch<P, K> rootHolder;

    public TreeImpl() {
        this.rootHolder = branchBuilder.build();
    }

    @Override
    public void put( P[] path, K hash ) {
        Leaf leaf = rootHolder;
        for ( int i = 0; i < path.length; i++ ) {
            if ( leaf instanceof Branch ) {
                Branch<P, K> branch =( (Branch<P, K>) leaf );
                Leaf leafNew = branch.enter( path[i] );
                if ( leafNew == null ) {
                    leafNew = branchBuilder.build();
                    branch.put( path[i], leafNew );
                }
                leaf = leafNew;
            }
            leaf.add( hash );
        }
    }

    @Override
    public Collection<K> get( P[] path ) {
        Leaf leaf = rootHolder;
        for ( P str : path ) {
            if ( leaf instanceof Branch ) {
                leaf = ( (Branch<P, K>) leaf ).enter( str );
            }
        }
        if ( leaf == null ) {
            return Collections.EMPTY_LIST;
        }
        return leaf.get();
    }

    @Override
    public Collection<K> getRemove( P[] path ) {
        Leaf leaf = rootHolder;
        for ( P str : path ) {
            if ( leaf != null && leaf instanceof Branch ) {
                leaf = ( (Branch<P, K>) leaf ).enter( str );
            }
        }
        if ( leaf != null ) {
            Collection<K> objects = leaf.get();
            rootHolder.remove( objects );
            return objects;
        }
        return Collections.EMPTY_LIST;
    }


}
