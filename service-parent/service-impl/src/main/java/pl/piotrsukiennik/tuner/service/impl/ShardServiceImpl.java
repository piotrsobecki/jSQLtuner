package pl.piotrsukiennik.tuner.service.impl;

import org.apache.commons.math.distribution.ContinuousDistribution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.piotrsukiennik.tuner.ShardNode;
import pl.piotrsukiennik.tuner.complexity.ComplexityEstimation;
import pl.piotrsukiennik.tuner.complexity.ComplexityEstimator;
import pl.piotrsukiennik.tuner.datasource.RecommendationContext;
import pl.piotrsukiennik.tuner.dto.ReadQueryExecutionResult;
import pl.piotrsukiennik.tuner.exception.DataRetrievalException;
import pl.piotrsukiennik.tuner.model.datasource.DataSourceIdentity;
import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.service.DataSourceRecommendationService;
import pl.piotrsukiennik.tuner.service.DataSourceSelector;
import pl.piotrsukiennik.tuner.service.QueryComplexityStatistics;
import pl.piotrsukiennik.tuner.service.QueryInvalidatonService;
import pl.piotrsukiennik.tuner.util.RowSet;

import javax.annotation.Resource;
import javax.sql.rowset.CachedRowSet;
import java.util.Collection;
import java.util.Map;

/**
 * Author: Piotr Sukiennik
 * Date: 31.08.13
 * Time: 18:58
 */
@Service
public class ShardServiceImpl extends AbstractShardServiceImpl {

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
    public boolean contains( ReadQuery query ) {
        //If list of data sources supporting query is not empty
        return !dataSourceSelector.supporting( query ).isEmpty();
    }

    @Override
    public ReadQueryExecutionResult get( ReadQuery query ) throws DataRetrievalException {
        ReadQueryExecutionResult readQueryExecutionResult = null;
        //Select the data source for query using dataSourceSelector
        DataSourceIdentity dataSourceIdentity = dataSourceSelector.select( query );
        if ( dataSourceIdentity != null ) {
            try {
                //Get using selected data source identity by the selector
                readQueryExecutionResult = getUsingDataSource( dataSourceIdentity,query );
            }
            catch ( DataRetrievalException dre ) {
                log( dre );
            }
        }
        //No data retreived
        if ( readQueryExecutionResult == null || readQueryExecutionResult.getResultSet() == null ) {
            //Retreive using root database
            readQueryExecutionResult = getUsingDefault( query );
        }
        //Propagate data if shardeable
        put( readQueryExecutionResult );
        //Log retrieval
        log( readQueryExecutionResult );
        //Feedback retrieval to data source selector for learning
        dataSourceSelector.submit( readQueryExecutionResult );
        return readQueryExecutionResult;
    }

    @Override
    public void put(  ReadQueryExecutionResult data ) {
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
        RecommendationContext.Builder<ReadQuery, ShardNode> recommendationBuilder =
         new RecommendationContext.Builder<ReadQuery, ShardNode>()
              .withDataSources( nodes )
              .withComplexityEstimation( complexityEstimation )
              .withReadQuery( data.getReadQuery() )
              .withDistributions( distributions );

        //Get possible nodes for sharding
        Collection<ShardNode> selectedNodes = dataSourceRecommendationService.possible(
         recommendationBuilder.build()
        );

        //Get all supporting data sources
        Collection<DataSourceIdentity> supporting = dataSourceSelector.supporting( data.getReadQuery() );
        //Nodes minus default data source
        int supportingNodes = supporting.size()>0?(supporting.size()-1):0;
        //If query could be distributed
        if ( selectedNodes.size() > supportingNodes){
            //Get new nodes
            Collection<ShardNode> newNodes = minus( selectedNodes, supporting );
            //Distribute data to selected nodes
            put( data,newNodes );
        }
    }
    protected void put(  ReadQueryExecutionResult data , Collection<ShardNode> selectedNodes  ) {
        ReadQuery query = data.getReadQuery();
        //Clone data for nodes
        CachedRowSet clonedData = RowSet.clone( data.getResultSet() );
        for ( ShardNode node : selectedNodes ) {
            //Distribute data for node
            put(query,node,clonedData);
        }
        //Inform invalidator service about new supported query
        invalidatorService.putCachedQuery( query );
    }

    protected void put(  ReadQuery query , ShardNode node, CachedRowSet rowSet){
        //Distribute data for each node
        node.put( query, rowSet );
        //Schedule the selection using the node
        dataSourceSelector.schedule( query, node );
    }

    @Override
    public void delete( Query query ) {
        //Get read queries that this query invalidates
        Collection<ReadQuery> queriesToInvalidate = query.invalidates( invalidatorService );
        //Remove option of selection
        for ( ReadQuery selectQuery : queriesToInvalidate ) {
            dataSourceSelector.removeOptions( selectQuery );
        }
        for (ReadQuery readQuery: queriesToInvalidate){
            //Get nodes on which this query is distributed
            Collection<DataSourceIdentity> selectedNodes = dataSourceSelector.supporting( readQuery );
            for (DataSourceIdentity dataSourceIdentity: selectedNodes){
                //DInvalidate queries for each node
                ShardNode shardNode = getShardNode( dataSourceIdentity );
                if (shardNode!=null){
                    shardNode.delete( query,queriesToInvalidate );
                }
            }
        }
    }

}
