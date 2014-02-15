package pl.piotrsukiennik.tuner.service.ai;

import pl.piotrsukiennik.ai.selectionhelper.UpdateableSelectionHelper;
import pl.piotrsukiennik.tuner.dto.DataRetrieval;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.model.query.datasource.DataSourceIdentity;
import pl.piotrsukiennik.tuner.service.ai.fitness.FitnessCalculator;
import pl.piotrsukiennik.tuner.service.ai.selection.DataSourceIdentifier;
import pl.piotrsukiennik.tuner.service.ai.selection.DataSourceSelectable;
import pl.piotrsukiennik.tuner.service.ai.selection.DataSourceSelectionHelper;
import pl.piotrsukiennik.tuner.service.util.Objects2;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Piotr Sukiennik
 * @date 13.02.14
 */
public class DataSourceSelectorImpl implements DataSourceSelector {

    private Map<String, DataSourceSelectionHelper<DataSourceSelectable>> selectionHelperForQuery = new LinkedHashMap<>();


    private Class<? extends UpdateableSelectionHelper<DataSourceSelectable>> selectionHelperClass;

    private FitnessCalculator fitnessCalculator;

    public Class<? extends UpdateableSelectionHelper<DataSourceSelectable>> getSelectionHelperClass() {
        return selectionHelperClass;
    }

    public void setSelectionHelperClass( Class<? extends UpdateableSelectionHelper<DataSourceSelectable>> selectionHelperClass ) {
        this.selectionHelperClass = selectionHelperClass;
    }

    public FitnessCalculator getFitnessCalculator() {
        return fitnessCalculator;
    }

    public void setFitnessCalculator( FitnessCalculator fitnessCalculator ) {
        this.fitnessCalculator = fitnessCalculator;
    }

    @Override
    public DataSourceIdentity selectDataSourceForQuery( ReadQuery readQuery ) {
        UpdateableSelectionHelper<DataSourceSelectable> selectionHelper = selectionHelperForQuery.get( readQuery.getHash() );
        if ( selectionHelper == null ) {
            return null;
        }
        DataSourceSelectable dataSourceSelectable = selectionHelper.select();
        if ( dataSourceSelectable == null ) {
            return null;
        }
        return dataSourceSelectable.getDataSource();
    }

    @Override
    public void submitDataRetrieval( DataSourceIdentity dataSource, ReadQuery readQuery, DataRetrieval dataRetrieval ) {
        DataSourceSelectionHelper<DataSourceSelectable> selectionHelper = selectionHelperForQuery.get( readQuery.getHash() );
        if ( selectionHelper == null ) {
            selectionHelper = new DataSourceSelectionHelper<DataSourceSelectable>( Objects2.newInstance( selectionHelperClass ), fitnessCalculator );
            selectionHelperForQuery.put( readQuery.getHash(), selectionHelper );
        }
        DataSourceSelectable sourceForQuery = selectionHelper.getSelection( new DataSourceIdentifier( dataSource ) );
        if ( sourceForQuery != null ) {
            sourceForQuery.updateExecutionTime( dataRetrieval.getExecutionTimeNano() );
        }
        else {
            sourceForQuery = new DataSourceSelectable( selectionHelper, dataSource, dataRetrieval.getExecutionTimeNano(), dataRetrieval.getRows() );
        }
    }

    @Override
    public void scheduleDataRetrieval( DataSourceIdentity dataSource, ReadQuery readQuery ) {
        DataSourceSelectionHelper<DataSourceSelectable> selectionHelper = selectionHelperForQuery.get( readQuery.getHash() );
        if ( selectionHelper == null ) {
            selectionHelper = new DataSourceSelectionHelper<DataSourceSelectable>( Objects2.newInstance( selectionHelperClass ), fitnessCalculator );
            selectionHelperForQuery.put( readQuery.getHash(), selectionHelper );
        }
        new DataSourceSelectable( selectionHelper, dataSource );
    }

    @Override
    public void removeDataSourcesForQuery( ReadQuery query ) {
        selectionHelperForQuery.remove( query.getHash() );
    }
}
