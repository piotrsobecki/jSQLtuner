package pl.piotrsukiennik.tuner.ai.impl;

import pl.piotrsukiennik.ai.selectionhelper.UpdateableSelectionHelper;
import pl.piotrsukiennik.tuner.DataSource;
import pl.piotrsukiennik.tuner.ai.DataSourceSelectable;
import pl.piotrsukiennik.tuner.ai.DataSourceSelectionHelper;
import pl.piotrsukiennik.tuner.ai.FitnessCalculator;
import pl.piotrsukiennik.tuner.dto.ReadQueryExecutionResult;
import pl.piotrsukiennik.tuner.model.datasource.DataSourceIdentity;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.service.QueryComplexityStatistics;
import pl.piotrsukiennik.tuner.util.GenericBuilder;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author Piotr Sukiennik
 * @date 13.02.14
 */
public class DataSourceSelectorImpl extends AbstractDataSourceSelectorImpl {

    @Resource
    private QueryComplexityStatistics queryComplexityStatistics;



    public DataSourceSelectorImpl(GenericBuilder<UpdateableSelectionHelper<DataSourceSelectable>> selectionHelperBuilder,FitnessCalculator fitnessCalculator) {
        super( fitnessCalculator, selectionHelperBuilder );
    }


    @Override
    public Collection<DataSourceIdentity> supporting( ReadQuery readQuery ) {
        List<DataSourceIdentity> dataSourceIdentities = new LinkedList<>(  );
        UpdateableSelectionHelper<DataSourceSelectable> selectionHelper = getDataSourceSelectionHelper( readQuery );
        Collection<DataSourceSelectable> dataSourceSelectables = selectionHelper.getOptions();
        for (DataSourceSelectable selectable: dataSourceSelectables){
            dataSourceIdentities.add( selectable.getDataSource() );
        }
        return dataSourceIdentities;
    }

    @Override
    public DataSourceIdentity select( ReadQuery readQuery ) {
        UpdateableSelectionHelper<DataSourceSelectable> selectionHelper = getDataSourceSelectionHelper( readQuery );
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
    public void submit( ReadQueryExecutionResult readQueryExecutionResult ) {
        ReadQuery readQuery = readQueryExecutionResult.getReadQuery();
        DataSourceIdentity dataSource = readQueryExecutionResult.getDataSource();
        DataSourceIdentifier dataSourceIdentifier =  new DataSourceIdentifier( dataSource );
        DataSourceSelectionHelper<DataSourceSelectable> selectionHelper = getDataSourceSelectionHelper( readQuery );
        DataSourceSelectable selectableDataSource = selectionHelper.getSelection( dataSourceIdentifier );
        if ( selectableDataSource == null ) {
            selectionHelper.submit( readQueryExecutionResult, new DataSourceSelectableImpl( readQueryExecutionResult ) );
        }
        else {
            selectableDataSource.updateExecutionTime( readQueryExecutionResult.getExecutionTimeNano() );
            selectionHelper.update( selectableDataSource );
        }
    }

    @Override
    public void schedule( ReadQuery readQuery, DataSource dataSource ) {
        DataSourceSelectionHelper<DataSourceSelectable> selectionHelper =getDataSourceSelectionHelper( readQuery );
        selectionHelper.schedule(  new DataSourceSelectableImpl( dataSource ) );
    }

}
