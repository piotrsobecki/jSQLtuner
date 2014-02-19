package pl.piotrsukiennik.tuner.ai.impl;

import pl.piotrsukiennik.ai.selectionhelper.UpdateableSelectionHelper;
import pl.piotrsukiennik.tuner.ai.DataSourceSelectable;
import pl.piotrsukiennik.tuner.ai.DataSourceSelectionHelper;
import pl.piotrsukiennik.tuner.ai.FitnessCalculator;
import pl.piotrsukiennik.tuner.dto.DataRetrieval;
import pl.piotrsukiennik.tuner.model.datasource.DataSourceIdentity;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.service.DataSourceSelector;
import pl.piotrsukiennik.tuner.util.Objects2;

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
    public DataSourceIdentity getDataSource( ReadQuery readQuery ) {
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
    public void submitDataRetrieval( DataRetrieval dataRetrieval ) {
        ReadQuery readQuery = dataRetrieval.getReadQuery();
        DataSourceIdentity dataSource = dataRetrieval.getDataSourceIdentity();
        DataSourceSelectionHelper<DataSourceSelectable> selectionHelper = selectionHelperForQuery.get( readQuery.getHash() );
        if ( selectionHelper == null ) {
            selectionHelper = new DataSourceSelectionHelperImpl<DataSourceSelectable>( Objects2.newInstance( selectionHelperClass ), fitnessCalculator );
            selectionHelperForQuery.put( readQuery.getHash(), selectionHelper );
        }
        DataSourceSelectable selectableDataSource = selectionHelper.getSelection( new DataSourceIdentifier( dataSource ) );
        if ( selectableDataSource != null ) {
            selectableDataSource.updateExecutionTime( dataRetrieval.getExecutionTimeNano() );
            selectionHelper.update( selectableDataSource );
        }
        else {
            selectableDataSource = new DataSourceSelectableImpl( dataRetrieval );
            selectionHelper.submit( selectableDataSource );
        }
    }

    @Override
    public void scheduleDataRetrieval( ReadQuery readQuery, DataSourceIdentity dataSource ) {
        DataSourceSelectionHelper<DataSourceSelectable> selectionHelper = selectionHelperForQuery.get( readQuery.getHash() );
        if ( selectionHelper == null ) {
            selectionHelper = new DataSourceSelectionHelperImpl<DataSourceSelectable>( Objects2.newInstance( selectionHelperClass ), fitnessCalculator );
            selectionHelperForQuery.put( readQuery.getHash(), selectionHelper );
        }
        DataSourceSelectable dataSourceSelectable = new DataSourceSelectableImpl( dataSource );
        selectionHelper.schedule( dataSourceSelectable );
    }

    @Override
    public void removeDataSources( ReadQuery query ) {
        selectionHelperForQuery.remove( query.getHash() );
    }
}
