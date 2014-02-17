package pl.piotrsukiennik.ai.selectionhelper.impl;

import pl.piotrsukiennik.ai.selectable.Selectable;
import pl.piotrsukiennik.ai.selectionhelper.CollectionSelection;
import pl.piotrsukiennik.ai.selectionhelper.SelectableComparator;
import pl.piotrsukiennik.ai.selectionhelper.UpdateableSelectionHelper;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Piotr Sukiennik
 * @date 12.02.14
 */
public class RouletteWheelSelection<T extends Selectable> extends CollectionSelection<T> implements UpdateableSelectionHelper<T> {

    private double selectableTotal = 0d;

    private Set<T> selectableSet = new TreeSet<T>( new SelectableComparator<T>() );

    @Override
    protected Collection<T> getCollection() {
        return selectableSet;
    }

    protected double random() {
        return Math.random() * selectableTotal;
    }

    @Override
    public T select() {
        double rand = random();
        double selSoFar = 0d;
        T returnValue = null;
        for ( T sel : selectableSet ) {
            selSoFar += sel.getFitness();
            returnValue = sel;
            if ( rand < selSoFar ) {
                return returnValue;
            }
        }
        return returnValue;
    }

    @Override
    public synchronized void submit( T selectable ) {
        selectableSet.add( selectable );
        selectableTotal = 0d;
        for ( Selectable sel : selectableSet ) {
            selectableTotal += sel.getFitness();
        }
    }
}
