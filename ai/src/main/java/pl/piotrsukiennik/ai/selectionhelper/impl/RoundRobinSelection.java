package pl.piotrsukiennik.ai.selectionhelper.impl;

import pl.piotrsukiennik.ai.selectable.Selectable;
import pl.piotrsukiennik.ai.selectionhelper.CollectionSelection;
import pl.piotrsukiennik.ai.selectionhelper.UpdateableSelectionHelper;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Piotr Sukiennik
 * @date 12.02.14
 */
public class RoundRobinSelection<T extends Selectable> extends CollectionSelection<T> implements UpdateableSelectionHelper<T> {

    private LinkedHashSet<T> selectableSet = new LinkedHashSet<T>();

    private Iterator<T> selectableIterator = selectableSet.iterator();

    @Override
    protected Set<T> getCollection() {
        return selectableSet;
    }

    public synchronized boolean update( T value ) {
        if ( super.update( value ) ) {
            reset();
            return true;
        }
        return false;
    }

    public void reset() {
        selectableIterator = selectableSet.iterator();
    }

    @Override
    public synchronized T select() {
        if ( !selectableIterator.hasNext() ) {
            reset();
        }
        if ( selectableIterator.hasNext() ) {
            return selectableIterator.next();
        }
        return null;
    }

    @Override
    public synchronized void submit( T selectable ) {
        selectableSet.add( selectable );
        reset();
    }
}
