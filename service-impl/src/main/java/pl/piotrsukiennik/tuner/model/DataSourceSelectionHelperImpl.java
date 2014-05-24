package pl.piotrsukiennik.tuner.model;

import pl.piotrsukiennik.ai.id.Identifier;
import pl.piotrsukiennik.ai.selectionhelper.UpdateableSelectionHelper;
import pl.piotrsukiennik.tuner.service.DataSourceFitnessService;
import pl.piotrsukiennik.tuner.service.DataSourceSelectable;
import pl.piotrsukiennik.tuner.service.DataSourceSelectionHelper;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Piotr Sukiennik
 * @date 14.02.14
 */
public class DataSourceSelectionHelperImpl<T extends DataSourceSelectable> implements UpdateableSelectionHelper<T>, DataSourceSelectionHelper<T> {

    private UpdateableSelectionHelper<T> updateableSelectionHelper;

    private Queue<T> scheduled = new LinkedList<T>();

    private DataSourceFitnessService dataSourceFitnessService;


    public DataSourceSelectionHelperImpl( UpdateableSelectionHelper<T> updateableSelectionHelper, DataSourceFitnessService dataSourceFitnessService ) {
        this.dataSourceFitnessService = dataSourceFitnessService;
        this.updateableSelectionHelper = updateableSelectionHelper;
    }

    @Override
    public synchronized boolean update( T t ) {
        t.setFitness( dataSourceFitnessService.calc( t ) );
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
        selectable.setFitness( dataSourceFitnessService.calc( selectable ) );
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
