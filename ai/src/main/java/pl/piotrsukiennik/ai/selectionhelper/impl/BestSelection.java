package pl.piotrsukiennik.ai.selectionhelper.impl;

import pl.piotrsukiennik.ai.selectable.Selectable;
import pl.piotrsukiennik.ai.selectionhelper.CollectionSelection;
import pl.piotrsukiennik.ai.selectionhelper.SelectableComparator;
import pl.piotrsukiennik.ai.selectionhelper.UpdateableSelectionHelper;

import java.util.Set;
import java.util.TreeSet;

/**
 * @author Piotr Sukiennik
 * @date 12.02.14
 */
public class BestSelection<T extends Selectable> extends CollectionSelection<T> implements UpdateableSelectionHelper<T> {

    private TreeSet<T> selectableSet = new TreeSet<T>( new SelectableComparator<T>() );

    @Override
    protected Set<T> getCollection() {
        return selectableSet;
    }

    @Override
    public T select() {
        if ( !selectableSet.isEmpty() ) {
            return selectableSet.first();
        }
        return null;
    }

    @Override
    public synchronized void submit( T selectable ) {
        selectableSet.add( selectable );

    }
}
