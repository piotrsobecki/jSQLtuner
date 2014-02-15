package pl.piotrsukiennik.ai.selectionhelper;

import pl.piotrsukiennik.ai.selectable.Selectable;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author Piotr Sukiennik
 * @date 12.02.14
 */
public class RoundRobinSelection<T extends Selectable> extends SetSelection<T> implements UpdateableSelectionHelper<T> {

    private LinkedHashSet<T> selectableSet = new LinkedHashSet<T>();

    private Iterator<T> selectableIterator = selectableSet.iterator();

    @Override
    protected Set<T> getCollection() {
        return selectableSet;
    }

    public synchronized boolean fitnessChanged( T value ) {
        if ( super.fitnessChanged( value ) ) {
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
