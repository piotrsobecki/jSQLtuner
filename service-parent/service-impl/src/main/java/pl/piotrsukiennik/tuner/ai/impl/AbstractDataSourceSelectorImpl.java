package pl.piotrsukiennik.tuner.ai.impl;

import pl.piotrsukiennik.ai.selectionhelper.UpdateableSelectionHelper;
import pl.piotrsukiennik.tuner.ai.DataSourceSelectable;
import pl.piotrsukiennik.tuner.ai.DataSourceSelectionHelper;
import pl.piotrsukiennik.tuner.ai.FitnessCalculator;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.service.DataSourceSelector;
import pl.piotrsukiennik.tuner.util.GenericBuilder;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Piotr Sukiennik
 * @date 22.05.14
 */
public abstract class AbstractDataSourceSelectorImpl implements DataSourceSelector {
    private Map<String, DataSourceSelectionHelper<DataSourceSelectable>> selectionHelperForQuery = new LinkedHashMap<>();

    private GenericBuilder<UpdateableSelectionHelper<DataSourceSelectable>> selectionHelperBuilder;

    private FitnessCalculator fitnessCalculator;

    public AbstractDataSourceSelectorImpl( FitnessCalculator fitnessCalculator, GenericBuilder<UpdateableSelectionHelper<DataSourceSelectable>> selectionHelperBuilder ) {
        this.fitnessCalculator=fitnessCalculator;
        this.selectionHelperBuilder=selectionHelperBuilder;
    }

    protected DataSourceSelectionHelper<DataSourceSelectable> getDataSourceSelectionHelper( ReadQuery readQuery ){
        DataSourceSelectionHelper<DataSourceSelectable> selectionHelper = selectionHelperForQuery.get( readQuery.getHash() );
        if ( selectionHelper == null ) {
            selectionHelper = new SchedulingSelectionHelper<DataSourceSelectable>(
             selectionHelperBuilder.build(),
             fitnessCalculator
            );
            selectionHelperForQuery.put( readQuery.getHash(), selectionHelper );
        }
        return selectionHelper;
    }

    @Override
    public void removeOptions( ReadQuery query ) {
        selectionHelperForQuery.remove( query.getHash() );
    }
}
