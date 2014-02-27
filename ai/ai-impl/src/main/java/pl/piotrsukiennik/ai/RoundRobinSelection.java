package pl.piotrsukiennik.ai;

import java.util.Set;
import java.util.TreeSet;

/**
 * @author Piotr Sukiennik
 * @date 12.02.14
 */
public class RoundRobinSelection implements SelectionHelper {

    private double selectableTotal = 0d;

    private Set<Selectable> selectableSet = new TreeSet<Selectable>( new SelectableComparator());

    protected double random(){
        return Math.random()*selectableTotal;
    }

    @Override
    public Selectable select() {
        double rand = random();
        double selSoFar=0d;
        Selectable returnValue=null;
        for (Selectable sel:selectableSet){
            selSoFar+=sel.getFitness();
            if (rand<selSoFar){
                return returnValue;
            }
            returnValue=sel;
        }
        return returnValue;
    }

    @Override
    public synchronized void submit( Selectable selectable ) {
        selectableSet.add( selectable );
        selectableTotal=0d;
        for (Selectable sel:selectableSet){
            selectableTotal+=sel.getFitness();
        }
    }
}
