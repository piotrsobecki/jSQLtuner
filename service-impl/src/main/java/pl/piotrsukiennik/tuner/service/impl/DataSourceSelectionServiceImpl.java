package pl.piotrsukiennik.tuner.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.piotrsukiennik.ai.selectionhelper.UpdateableSelectionHelper;
import pl.piotrsukiennik.tuner.model.*;
import pl.piotrsukiennik.tuner.model.datasource.DataSourceIdentity;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.service.*;
import pl.piotrsukiennik.tuner.statistics.CumulativeProbabilityCapable;
import pl.piotrsukiennik.tuner.util.GenericBuilder;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author Piotr Sukiennik
 * @date 13.02.14
 */
@Service
public class DataSourceSelectionServiceImpl implements DataSourceSelectionService {

    @Autowired
    private DataSourceService dataSourceService;

    @Autowired
    private QueryExecutionComplexityStatisticsService queryExecutionComplexityStatisticsService;

    @Autowired
    private DataSourceRecommendationService<ReadQuery> dataSourceRecommendationService;


    @Resource(name = "dataSourceSelectionHelperBuilder")
    private GenericBuilder<DataSourceSelectionHelper<DataSourceSelectable>> selectionHelperBuilder;

    private Map<String, DataSourceSelectionHelper<DataSourceSelectable>> selectionHelperForQuery =
     new HashMap<>();

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
        ReadQuery readQuery = data.getQuery();
        DataSourceIdentity dataSourceIdentity = data.getDataSource();
        DataSourceIdentifier dataSourceIdentifier =  new DataSourceIdentifier( dataSourceIdentity );
        DataSourceSelectionHelper<DataSourceSelectable> selectionHelper = getDataSourceSelectionHelper( readQuery );
        DataSourceSelectable selectableDataSource = selectionHelper.getSelection( dataSourceIdentifier );
        if ( selectableDataSource == null ) {
            selectionHelper.submit( data, new DataSourceSelectableImpl(
                 data.getReadQueryExecutionComplexityEstimation(),
                 dataSourceIdentifier
            ));
        }
        else {
            //Get readQueryExecutionComplexityEstimation
            ReadQueryExecutionComplexityEstimation readQueryExecutionComplexityEstimationImpl = data.getReadQueryExecutionComplexityEstimation();
            selectableDataSource.update( readQueryExecutionComplexityEstimationImpl );
            selectionHelper.update( selectableDataSource );
        }
    }

    @Override
    public Collection<DataSourceIdentity>  getNewSupportingDataSources(ReadQueryExecutionResult data ){
        //Get all supporting data sources for query
        Collection<DataSourceIdentity> supportingDataSources = getSupportingDataSources( data.getQuery() );
        int supportingDataSourcesSize = supportingDataSources.size();
        //Nodes minus default data source
        int supportingNodesSize = supportingDataSourcesSize>0?(supportingDataSourcesSize-1):0;

        //Get all data sources
        Collection<DataSourceIdentity> allDataSourceIdentities = dataSourceService.getDataSourceIdentities();

        //If nothing new can be introduced
        if (supportingNodesSize>=allDataSourceIdentities.size()){
            return Collections.EMPTY_LIST;
        }

        //Get readQueryExecutionComplexityEstimation
        ExecutionComplexityEstimation executionComplexityEstimation = data.getReadQueryExecutionComplexityEstimation();
        //Complexity distributions for query type
        Map<ReadQueryExecutionComplexityEstimationImpl.Type,? extends CumulativeProbabilityCapable> distributions
         = queryExecutionComplexityStatisticsService.getDistributions( data.getQuery() );
        //Get dataSources possible for sharding
        Collection<DataSourceIdentity> selectedNodes = dataSourceRecommendationService.possible(
            new DataSourceRecommendationContextBuilder<>()
                 .withDataSources( allDataSourceIdentities )
                 .withComplexityEstimation( executionComplexityEstimation )
                 .withReadQuery( data.getQuery() )
                 .withDistributions( distributions )
             .build()
        );
        //New Nodes
        int newNodesSize = selectedNodes.size() - supportingNodesSize;
        Collection<DataSourceIdentity> newNodes = null;
        //If there are some new dataSources for query
        if ( newNodesSize > 0){
            //Get new dataSources
            newNodes = new HashSet<>(selectedNodes);
            newNodes.removeAll( supportingDataSources );
        } else {
            newNodes = Collections.EMPTY_LIST;
        }
        return newNodes;
    }


    public void updateComplexityEstimations(ReadQueryExecutionResult data){
        //Get readQueryExecutionComplexityEstimation
        ReadQueryExecutionComplexityEstimation readQueryExecutionComplexityEstimation =  data.getReadQueryExecutionComplexityEstimation();
        //If data has been received using default dataSource
        if (data.getDataSource().getDefaultDataSource()){
            //Provide execution data
            queryExecutionComplexityStatisticsService.increment( data.getQuery(), readQueryExecutionComplexityEstimation );
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



    protected DataSourceSelectionHelper<DataSourceSelectable> getDataSourceSelectionHelper( ReadQuery readQuery ){
        DataSourceSelectionHelper<DataSourceSelectable> selectionHelper = selectionHelperForQuery.get( readQuery.getHash() );
        if ( selectionHelper == null ) {
            selectionHelperForQuery.put(
                 readQuery.getHash(),
                 selectionHelperBuilder.build()
            );
        }
        return selectionHelper;
    }

    @Override
    public void removeSelectionOptions( ReadQuery query ) {
        selectionHelperForQuery.remove( query.getHash() );
    }
}
