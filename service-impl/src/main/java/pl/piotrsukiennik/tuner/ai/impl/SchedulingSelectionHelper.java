package pl.piotrsukiennik.tuner.ai.impl;

import pl.piotrsukiennik.ai.id.Identifier;
import pl.piotrsukiennik.ai.selectionhelper.UpdateableSelectionHelper;
import pl.piotrsukiennik.tuner.ai.DataSourceSelectable;
import pl.piotrsukiennik.tuner.ai.DataSourceSelectionHelper;
import pl.piotrsukiennik.tuner.ai.FitnessCalculator;
import pl.piotrsukiennik.tuner.dto.ReadQueryExecutionResult;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Piotr Sukiennik
 * @date 14.02.14
 */
public class SchedulingSelectionHelper<T extends DataSourceSelectable> implements UpdateableSelectionHelper<T>, DataSourceSelectionHelper<T> {

    private UpdateableSelectionHelper<T> updateableSelectionHelper;

    private Queue<T> scheduled = new LinkedList<T>();

    private FitnessCalculator fitnessCalculator;


    public SchedulingSelectionHelper( UpdateableSelectionHelper<T> updateableSelectionHelper, FitnessCalculator fitnessCalculator ) {
        this.fitnessCalculator = fitnessCalculator;
        this.updateableSelectionHelper = updateableSelectionHelper;
    }

    @Override
    public synchronized boolean update( T t ) {
        t.setFitness( fitnessCalculator.calc( t ) );
        return updateableSelectionHelper.update( t );
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
        if ( scheduled.isEmpty() ) {
            return updateableSelectionHelper.select();
        }
        return scheduled.poll();

    }

    @Override
    public void submit( T selectable ) {
        scheduled.add( selectable );
        updateableSelectionHelper.submit( selectable );
    }

    @Override
    public void submit( ReadQueryExecutionResult readQueryExecutionResult, T selectable ) {
        selectable.setFitness( fitnessCalculator.calc( selectable ) );
        updateableSelectionHelper.submit( selectable );
    }

    @Override
    public void schedule( T selectable ) {
        if (!scheduled.contains( selectable )){
            scheduled.add( selectable );
        }
    }


    @Override
    public T getSelection( Identifier identifier ) {
        return updateableSelectionHelper.getSelection( identifier );
    }

    @Override
    public Collection<T> getOptions() {
        return updateableSelectionHelper.getOptions();
    }
}
