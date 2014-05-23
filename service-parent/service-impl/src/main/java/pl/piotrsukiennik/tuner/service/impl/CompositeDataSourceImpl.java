package pl.piotrsukiennik.tuner.service.impl;

import org.apache.commons.math.distribution.ContinuousDistribution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.piotrsukiennik.tuner.CompositeDataSource;
import pl.piotrsukiennik.tuner.DataSource;
import pl.piotrsukiennik.tuner.DataSources;
import pl.piotrsukiennik.tuner.LoggableService;
import pl.piotrsukiennik.tuner.complexity.ComplexityEstimation;
import pl.piotrsukiennik.tuner.complexity.ComplexityEstimator;
import pl.piotrsukiennik.tuner.datasource.RecommendationContext;
import pl.piotrsukiennik.tuner.dto.ReadQueryExecutionResult;
import pl.piotrsukiennik.tuner.dto.ReadQueryExecutionResultBuilder;
import pl.piotrsukiennik.tuner.exception.DataRetrievalException;
import pl.piotrsukiennik.tuner.model.datasource.DataSourceIdentity;
import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.service.*;
import pl.piotrsukiennik.tuner.util.RowSet;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.util.*;

/**
 * Author: Piotr Sukiennik
 * Date: 31.08.13
 * Time: 18:58
 */
@Service
public class CompositeDataSourceImpl implements CompositeDataSource {

    private Set<DataSource> dataSources = new LinkedHashSet<>( );

    @Autowired
    private DataSourceService dataSourceService;

    @Autowired
    private ReadQueryExecutionService readQueryExecutionService;

    @Autowired
    private LoggableService loggableService;

    @Autowired
    private QueryInvalidatonService invalidatorService;

    @Autowired
    private DataSourceSelector dataSourceSelector;

    @Autowired
    private QueryComplexityStatistics complexityStatistics;

    @Resource
    private DataSourceRecommendationService<ReadQuery> dataSourceRecommendationService;

    @Resource
    private ComplexityEstimator complexityEstimator;

    @Override
    public DataSourceIdentity getDataSourceIdentity() {
        return new DataSourceIdentity( CompositeDataSourceImpl.class,"CompositeDataSource" );
    }

    @Override
    public ResultSet execute( ReadQuery query ) throws DataRetrievalException {
        return doExecute( query ).getResultSet();
    }

    protected ReadQueryExecutionResult doExecute( ReadQuery query ) throws DataRetrievalException {
        ReadQueryExecutionResult readQueryExecutionResult = null;
        //Select the data source for query using dataSourceSelector
        DataSourceIdentity dataSourceIdentity = dataSourceSelector.selectDataSource( query );
        if ( dataSourceIdentity != null ) {
            try {
                //Get using selected data source identity by the selector
                readQueryExecutionResult = executeOnDataSource( dataSourceIdentity, query );
            }
            catch ( DataRetrievalException dre ) {
                log( dre );
            }
        }
        //No data retreived
        if ( readQueryExecutionResult == null || readQueryExecutionResult.getResultSet() == null ) {
            //Retreive using root database
            readQueryExecutionResult = executeOnDefaultDataSource( query );
        }
        //Propagate data if shardeable
        distribute( readQueryExecutionResult );
        //Log retrieval
        log( readQueryExecutionResult );
        //Feedback retrieval to data source selector for learning
        dataSourceSelector.submitExecution( readQueryExecutionResult );
        return readQueryExecutionResult;
    }

    @Override
    public void distribute( ReadQueryExecutionResult data ) {
        //Get complexityEstimation
        ComplexityEstimation complexityEstimation = complexityEstimator.estimate( data );

        //Complexity distributions for query type
        Map<ComplexityEstimation.Type,? extends ContinuousDistribution> distributions;
        //If data has been received using default dataSource
        if (data.getDataSource().getDefaultDataSource()){
            //Provide execution data
            distributions =  complexityStatistics.increment( data.getReadQuery(), complexityEstimation );
        } else {
            //Just receive execution data
            distributions =  complexityStatistics.getDistributions( data.getReadQuery() );
        }
        //Prepare RecommendationContext builder
        RecommendationContext.Builder<ReadQuery, DataSource> recommendationBuilder =
         new RecommendationContext.Builder<ReadQuery, DataSource>()
              .withDataSources( dataSources )
              .withComplexityEstimation( complexityEstimation )
              .withReadQuery( data.getReadQuery() )
              .withDistributions( distributions );

        //Get dataSources possible for sharding
        Collection<DataSource> selectedNodes = dataSourceRecommendationService.possible(
            recommendationBuilder.build()
        );
        //Get all supporting data sources
        Collection<DataSourceIdentity> supportingDataSources = dataSourceSelector.getSupportingDataSources( data.getReadQuery() );
        int supportingDataSourcesSize = supportingDataSources.size();
        //Nodes minus default data source
        int supportingNodesSize = supportingDataSourcesSize>0?(supportingDataSourcesSize-1):0;
        //New Nodes
        int newNodesSize = selectedNodes.size() - supportingNodesSize;
        //If there are some new dataSources for query
        if ( newNodesSize > 0){
            //Get new dataSources
            Collection<DataSource> newNodes = minus( selectedNodes, supportingDataSources );
            //Clone the result set
            ReadQueryExecutionResult copy =  new ReadQueryExecutionResultBuilder( data )
                 .withResultSet( RowSet.clone( data.getResultSet() ) )
                 .build();
            //Distribute data to selected dataSources
            this.distribute( newNodes,copy );
        }
    }


    protected void distribute(  Collection<DataSource> selectedNodes,  ReadQueryExecutionResult data ) {
        ReadQuery query = data.getReadQuery();
        //Clone data for dataSources
        for ( DataSource node : selectedNodes ) {
            //Distribute data for node
            node.distribute( data );
            //Schedule the selection using the node
            dataSourceSelector.scheduleSelection( query, node );
        }
        //Inform invalidator service about new supported query
        invalidatorService.putCachedQuery( query );
    }


    @Override
    public void delete( Query query ) {
        //Get read queries that this query invalidates
        Collection<ReadQuery> queriesToInvalidate = query.invalidates( invalidatorService );
        //Remove option of selection
        for ( ReadQuery selectQuery : queriesToInvalidate ) {
            dataSourceSelector.removeSelectionOptions( selectQuery );
        }
    }

    protected ReadQueryExecutionResult executeOnDefaultDataSource( ReadQuery query ) throws DataRetrievalException {
        DataSource dataSource =  dataSourceService.getDataSourceDefault( query );
        return readQueryExecutionService.execute( dataSource, query );
    }

    protected ReadQueryExecutionResult executeOnDataSource( DataSourceIdentity dataSourceIdentity, ReadQuery query ) throws DataRetrievalException {
        DataSource dataSource =dataSourceService.getDataSource( query, dataSourceIdentity );
        if (dataSource!=null){
            return readQueryExecutionService.execute( dataSource, query );
        }
        return null;
    }

    protected Collection<DataSource> minus(Collection<DataSource> dataSources, Collection<DataSourceIdentity> dataSourceIdentities){
        //Prepare output list
        Collection<DataSource> newNodes = new LinkedList<>(  );
        //filter dataSources
        for (DataSource shardNode: dataSources ){
            //If no data source identity in identities list
            if (!dataSourceIdentities.contains( shardNode.getDataSourceIdentity())){
                //Add node to output
                newNodes.add( shardNode );
            }
        }
        return newNodes;
    }
    protected void log( DataRetrievalException exception ){
        loggableService.log( exception );
    }

    protected void log( ReadQueryExecutionResult data ){
        loggableService.log( data );
    }

    @Autowired
    public void setDataSources( Collection<DataSource> dataSources ) {
        for (DataSource dataSource: dataSources ){
            if (dataSource!=this){
                add( dataSource );
            }
        }
    }

    @Override
    public void add( DataSource dataSource ) {
        if (dataSource!=this){
            dataSources.add( dataSource );
        }
    }

    @Override
    public void remove( DataSourceIdentity dataSourceIdentity ) {
        DataSource dataSource = DataSources.filterByIdentity(dataSources,dataSourceIdentity);
        dataSources.remove( dataSource );
    }

    public void setDefaultDataSource( ReadQuery query, DataSource dataSource ) {
        dataSourceService.setDefault( query, dataSource );
    }
}
