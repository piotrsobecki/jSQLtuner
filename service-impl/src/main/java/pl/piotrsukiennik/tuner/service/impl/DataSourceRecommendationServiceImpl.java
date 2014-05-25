package pl.piotrsukiennik.tuner.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.piotrsukiennik.tuner.Property;
import pl.piotrsukiennik.tuner.model.DataSourceRecommendationContext;
import pl.piotrsukiennik.tuner.model.ReadQueryExecutionComplexityEstimationImpl;
import pl.piotrsukiennik.tuner.model.datasource.DataSourceIdentity;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.service.DataSourceRecommendationService;
import pl.piotrsukiennik.tuner.statistics.CumulativeProbabilityCapable;

import java.util.Collection;
import java.util.Collections;

/**
 * @author Piotr Sukiennik
 * @date 22.05.14
 */
@Service
public class DataSourceRecommendationServiceImpl<RQ extends ReadQuery> implements DataSourceRecommendationService<RQ> {

    private static final Log LOG = LogFactory.getLog( DataSourceRecommendationServiceImpl.class );

    private final static double DEFAULT_REQUIRED_CUMULATIVE_PROBABILITY = 0.1;

    private double requiredCumulativeProbability=DEFAULT_REQUIRED_CUMULATIVE_PROBABILITY;

    private static final String MESSAGE_RETURNING_NODES = "Returning nodes: %s";
    private static final String MESSAGE_SHARDABLE = "Context | Shardable | Cumulative Probability %s | Complexity Estimation %s";
    private static final String MESSAGE_NOT_SHARDABLE = "Context | Not Shardable | Cumulative Probability %s | Complexity Estimation %s";

    @Autowired
    public DataSourceRecommendationServiceImpl( @Value( "${"+ Property.COMPLEXITY_DISTRIBUTION_CUMULATIVE +"}" )double requiredCumulativeProbability ) {
        this.requiredCumulativeProbability = requiredCumulativeProbability;
    }

    @Override
    public <DS extends DataSourceIdentity> Collection<DS> recommended( DataSourceRecommendationContext<RQ, DS> context ){
        if (isShardable( context )){
            Collection<DS> nodes =  context.getNodes();
            if (LOG.isDebugEnabled()){
                LOG.debug( String.format(MESSAGE_RETURNING_NODES,nodes) );
            }
            return nodes;
        }
        return Collections.EMPTY_LIST;
    }


    public <DS extends DataSourceIdentity> boolean isShardable( DataSourceRecommendationContext<RQ,DS> context ){
        CumulativeProbabilityCapable distribution = context.getQueryDistributions().get( ReadQueryExecutionComplexityEstimationImpl.Type.EXECUTION_COMPLEXITY );
        double cumulativeProbability = distribution.cumulativeProbability(
         context.getReadQueryExecutionComplexityEstimation().getExecutionComplexity()
        );
        if ( requiredCumulativeProbability <=cumulativeProbability){
            if (LOG.isDebugEnabled()){
                LOG.debug( String.format(MESSAGE_SHARDABLE,cumulativeProbability,context.getReadQueryExecutionComplexityEstimation()) );
            }
            return true;
        }
        if (LOG.isDebugEnabled()){
            LOG.debug( String.format(MESSAGE_NOT_SHARDABLE,cumulativeProbability,context.getReadQueryExecutionComplexityEstimation()) );
        }
        return false;
    }
}
