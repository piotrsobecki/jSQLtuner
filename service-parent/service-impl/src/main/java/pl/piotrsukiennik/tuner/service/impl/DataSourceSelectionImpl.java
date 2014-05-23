package pl.piotrsukiennik.tuner.service.impl;

import org.apache.commons.math.distribution.ContinuousDistribution;
import org.springframework.beans.factory.annotation.Autowired;
import pl.piotrsukiennik.ai.selectionhelper.UpdateableSelectionHelper;
import pl.piotrsukiennik.tuner.DataSources;
import pl.piotrsukiennik.tuner.ai.DataSourceSelectable;
import pl.piotrsukiennik.tuner.ai.DataSourceSelectionHelper;
import pl.piotrsukiennik.tuner.ai.FitnessCalculator;
import pl.piotrsukiennik.tuner.ai.impl.AbstractDataSourceSelectionImpl;
import pl.piotrsukiennik.tuner.ai.impl.DataSourceIdentifier;
import pl.piotrsukiennik.tuner.ai.impl.DataSourceSelectableImpl;
import pl.piotrsukiennik.tuner.complexity.ComplexityEstimation;
import pl.piotrsukiennik.tuner.complexity.ComplexityEstimator;
import pl.piotrsukiennik.tuner.datasource.RecommendationContext;
import pl.piotrsukiennik.tuner.dto.ReadQueryExecutionResult;
import pl.piotrsukiennik.tuner.model.datasource.DataSourceIdentity;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.service.DataSourceRecommendationService;
import pl.piotrsukiennik.tuner.service.QueryComplexityStatistics;
import pl.piotrsukiennik.tuner.util.GenericBuilder;

import java.util.*;

/**
 * @author Piotr Sukiennik
 * @date 13.02.14
 */
public class DataSourceSelectionImpl extends AbstractDataSourceSelectionImpl {

    @Autowired
    private QueryComplexityStatistics queryComplexityStatistics;

    @Autowired
    private DataSourceRecommendationService<ReadQuery> dataSourceRecommendationService;

    @Autowired
    private ComplexityEstimator complexityEstimator;

    public DataSourceSelectionImpl( GenericBuilder<UpdateableSelectionHelper<DataSourceSelectable>> selectionHelperBuilder, FitnessCalculator fitnessCalculator ) {
        super( fitnessCalculator, selectionHelperBuilder );
    }

    @Override
    public Collection<DataSourceIdentity> getSupportingDataSources( ReadQuery readQuery ) {
        List<DataSourceIdentity> dataSourceIdentities = new LinkedList<>(  );
        UpdateableSelectionHelper<DataSourceSelectable> selectionHelper = getDataSourceSelectionHelper( readQuery );
        Collection<DataSourceSelectable> dataSourceSelectables = selectionHelper.getOptions();
        for (DataSourceSelectable selectable: dataSourceSelectables){
            dataSourceIdentities.add( selectable.getDataSource() );
        }
        return dataSourceIdentities;
    }

    @Override
    public DataSourceIdentity selectDataSource( ReadQuery readQuery ) {
        UpdateableSelectionHelper<DataSourceSelectable> selectionHelper = getDataSourceSelectionHelper( readQuery );
        if ( selectionHelper == null ) {
            return null;
        }
        DataSourceSelectable dataSourceSelectable = selectionHelper.select();
        if ( dataSourceSelectable == null ) {
            return null;
        }
        return  dataSourceSelectable.getDataSource() ;
    }

    protected void updateSelectionHelpers( ReadQueryExecutionResult data ){
        ReadQuery readQuery = data.getReadQuery();
        DataSourceIdentity dataSourceIdentity = data.getDataSource();
        DataSourceIdentifier dataSourceIdentifier =  new DataSourceIdentifier( dataSourceIdentity );
        DataSourceSelectionHelper<DataSourceSelectable> selectionHelper = getDataSourceSelectionHelper( readQuery );
        DataSourceSelectable selectableDataSource = selectionHelper.getSelection( dataSourceIdentifier );
        if ( selectableDataSource == null ) {
            selectionHelper.submit( data, new DataSourceSelectableImpl( data,dataSourceIdentifier) );
        }
        else {
            selectableDataSource.updateExecutionTime( data.getExecutionTimeNano() );
            selectionHelper.update( selectableDataSource );
        }
    }

    @Override
    public Collection<DataSourceIdentity>  getNewSupportingDataSources(
     Collection<DataSourceIdentity> dataSourceIdentities,
     ReadQueryExecutionResult data
    ){
        //Get all supporting data sources
        Collection<DataSourceIdentity> supportingDataSources = getSupportingDataSources( data.getReadQuery() );

        //Get complexityEstimation
        ComplexityEstimation complexityEstimation = complexityEstimator.estimate( data );
        //Complexity distributions for query type
        Map<ComplexityEstimation.Type,? extends ContinuousDistribution> distributions
         =queryComplexityStatistics.getDistributions( data.getReadQuery() );
        //Prepare RecommendationContext builder
        RecommendationContext.Builder<ReadQuery, DataSourceIdentity> recommendationBuilder =
         new RecommendationContext.Builder<ReadQuery, DataSourceIdentity>()
          .withDataSources( dataSourceIdentities )
          .withComplexityEstimation( complexityEstimation )
          .withReadQuery( data.getReadQuery() )
          .withDistributions( distributions );

        //Get dataSources possible for sharding
        Collection<DataSourceIdentity> selectedNodes = dataSourceRecommendationService.possible(
         recommendationBuilder.build()
        );

        int supportingDataSourcesSize = supportingDataSources.size();
        //Nodes minus default data source
        int supportingNodesSize = supportingDataSourcesSize>0?(supportingDataSourcesSize-1):0;
        //New Nodes
        int newNodesSize = selectedNodes.size() - supportingNodesSize;
        Collection<DataSourceIdentity> newNodes = null;
        //If there are some new dataSources for query
        if ( newNodesSize > 0){
            //Get new dataSources
            newNodes = DataSources.minus( selectedNodes, supportingDataSources );
        } else {
            newNodes = Collections.EMPTY_LIST;
        }
        return newNodes;
    }


    public void updateComplexityEstimations(ReadQueryExecutionResult data){
        //Get complexityEstimation
        ComplexityEstimation complexityEstimation = complexityEstimator.estimate( data );
        //If data has been received using default dataSource
        if (data.getDataSource().getDefaultDataSource()){
            //Provide execution data
            queryComplexityStatistics.increment( data.getReadQuery(), complexityEstimation );
        }
    }

    @Override
    public void submitExecution(  ReadQueryExecutionResult data ) {
        updateSelectionHelpers(data);
        updateComplexityEstimations( data );
    }

    @Override
    public void scheduleSelection( ReadQuery readQuery, DataSourceIdentity dataSource ) {
        DataSourceSelectionHelper<DataSourceSelectable> selectionHelper =getDataSourceSelectionHelper( readQuery );
        selectionHelper.schedule( new DataSourceSelectableImpl( dataSource ) );
    }


}
