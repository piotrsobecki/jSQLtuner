package pl.piotrsukiennik.ai.selectionhelper;

import pl.piotrsukiennik.ai.selectable.Selectable;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Piotr Sukiennik
 * @date 12.02.14
 */
public class RouletteWheelSelection<T extends Selectable> extends SetSelection<T> implements UpdateableSelectionHelper<T> {

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
