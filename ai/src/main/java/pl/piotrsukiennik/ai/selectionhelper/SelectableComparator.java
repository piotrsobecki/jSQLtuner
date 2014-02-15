package pl.piotrsukiennik.ai.selectionhelper;

import pl.piotrsukiennik.ai.selectable.Selectable;

import java.util.Comparator;

/**
 * @author Piotr Sukiennik
 * @date 12.02.14
 */
public class SelectableComparator<T extends Selectable> implements Comparator<T> {
    @Override
    public int compare( T o1, T o2 ) {
        int i = Double.compare( o2.getFitness(), o1.getFitness() );
        if ( i == 0 ) {
            return o1.getIdentifier().compareTo( o2.getIdentifier() );
        }
        else {
            return i;
        }
    }
}
