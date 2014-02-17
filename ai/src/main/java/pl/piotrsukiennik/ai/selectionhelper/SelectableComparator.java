package pl.piotrsukiennik.ai.selectionhelper;

import pl.piotrsukiennik.ai.selectable.Selectable;
import pl.piotrsukiennik.ai.utils.Comparables;

import java.util.Comparator;

/**
 * @author Piotr Sukiennik
 * @date 12.02.14
 */
public class SelectableComparator<T extends Selectable> implements Comparator<T> {
    @Override
    public int compare( T o1, T o2 ) {
        int i = Comparables.compare( o2.getFitness(), o1.getFitness() );
        if ( i == 0 ) {
            return Comparables.compare( o1.getIdentifier(), o2.getIdentifier() );
        }
        return i;
    }
}
