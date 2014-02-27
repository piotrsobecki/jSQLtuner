package pl.piotrsukiennik.ai;

import java.util.Set;
import java.util.TreeSet;

/**
 * @author Piotr Sukiennik
 * @date 12.02.14
 */
public class BestSelection implements SelectionHelper {

    private Set<Selectable> selectableSet = new TreeSet<Selectable>( new SelectableComparator());

    @Override
    public Selectable select() {
       if (!selectableSet.isEmpty()){
           return selectableSet.iterator().next();
       }
       return null;
    }

    @Override
    public synchronized void submit( Selectable selectable ) {
        selectableSet.add( selectable );
    }
}
