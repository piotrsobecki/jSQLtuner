package pl.piotrsukiennik.tuner.service.ai.selection;

import pl.piotrsukiennik.ai.id.Identifier;
import pl.piotrsukiennik.ai.selectionhelper.UpdateableSelectionHelper;
import pl.piotrsukiennik.tuner.service.ai.fitness.FitnessCalculator;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Piotr Sukiennik
 * @date 14.02.14
 */
public class DataSourceSelectionHelper<T extends DataSourceSelectable> implements UpdateableSelectionHelper<T> {

    private UpdateableSelectionHelper<T> updateableSelectionHelper;

    private Queue<T> scheduled = new LinkedList<T>();

    private FitnessCalculator fitnessCalculator;

    public DataSourceSelectionHelper( UpdateableSelectionHelper<T> updateableSelectionHelper, FitnessCalculator fitnessCalculator ) {
        this.fitnessCalculator = fitnessCalculator;
        this.updateableSelectionHelper = updateableSelectionHelper;
    }


    protected synchronized void updateFitness( T t ) {
        t.setFitness( fitnessCalculator.calc( t ) );
        fitnessChanged( t );
    }


    public boolean fitnessChanged( T value ) {

        return updateableSelectionHelper.fitnessChanged( value );
    }

    public boolean removeOption( T value ) {
        return updateableSelectionHelper.removeOption( value );
    }

    @Override
    public boolean removeOption( Identifier optionIdentifier ) {
        return updateableSelectionHelper.removeOption( optionIdentifier );
    }


    @Override
    public T select() {
        if ( !scheduled.isEmpty() ) {
            return scheduled.poll();
        }
        else {
            return updateableSelectionHelper.select();

        }
    }

    public void submit( T selectable ) {
        updateableSelectionHelper.submit( selectable );
    }

    public void scheduleForSelection( T selectable ) {
        scheduled.add( selectable );
    }


    @Override
    public T getSelection( Identifier identifier ) {
        return updateableSelectionHelper.getSelection( identifier );
    }
}
