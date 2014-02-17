package pl.piotrsukiennik.tuner.service.ai.impl;

import pl.piotrsukiennik.ai.id.Identifier;
import pl.piotrsukiennik.ai.selectionhelper.UpdateableSelectionHelper;
import pl.piotrsukiennik.tuner.service.ai.DataSourceSelectable;
import pl.piotrsukiennik.tuner.service.ai.DataSourceSelectionHelper;
import pl.piotrsukiennik.tuner.service.ai.FitnessCalculator;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Piotr Sukiennik
 * @date 14.02.14
 */
public class DataSourceSelectionHelperImpl<T extends DataSourceSelectable> implements UpdateableSelectionHelper<T>, DataSourceSelectionHelper<T> {

    private UpdateableSelectionHelper<T> updateableSelectionHelper;

    private Queue<T> scheduled = new LinkedList<T>();

    private FitnessCalculator fitnessCalculator;

    public DataSourceSelectionHelperImpl( UpdateableSelectionHelper<T> updateableSelectionHelper, FitnessCalculator fitnessCalculator ) {
        this.fitnessCalculator = fitnessCalculator;
        this.updateableSelectionHelper = updateableSelectionHelper;
    }


    public synchronized void updateFitness( T t ) {
        t.setFitness( fitnessCalculator.calc( t ) );
        fitnessChanged( t );
    }


    @Override
    public boolean fitnessChanged( T value ) {
        return updateableSelectionHelper.fitnessChanged( value );
    }

    @Override
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

    @Override
    public void submit( T selectable ) {
        updateableSelectionHelper.submit( selectable );
    }

    @Override
    public void scheduleForSelection( T selectable ) {
        scheduled.add( selectable );
    }


    @Override
    public T getSelection( Identifier identifier ) {
        return updateableSelectionHelper.getSelection( identifier );
    }
}
