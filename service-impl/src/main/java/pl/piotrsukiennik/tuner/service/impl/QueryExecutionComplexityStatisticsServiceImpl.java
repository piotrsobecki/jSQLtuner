package pl.piotrsukiennik.tuner.service.impl;

import org.springframework.stereotype.Service;
import pl.piotrsukiennik.tuner.model.ReadQueryExecutionComplexityEstimation;
import pl.piotrsukiennik.tuner.model.ReadQueryExecutionComplexityEstimationImpl;
import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.service.QueryExecutionComplexityStatisticsService;
import pl.piotrsukiennik.tuner.statistics.CumulativeProbabilityCapable;
import pl.piotrsukiennik.tuner.statistics.IncrementalContinuousDistribution;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Piotr Sukiennik
 * @date 22.05.14
 */
@Service
public class QueryExecutionComplexityStatisticsServiceImpl implements QueryExecutionComplexityStatisticsService {

    private Map<Class<? extends Query>, Map<ReadQueryExecutionComplexityEstimationImpl.Type,IncrementalContinuousDistribution>> distributions;

    @Override
    public Map<ReadQueryExecutionComplexityEstimationImpl.Type, ? extends CumulativeProbabilityCapable> getDistributions( ReadQuery query ) {
        Map<ReadQueryExecutionComplexityEstimationImpl.Type,IncrementalContinuousDistribution> map = new HashMap<>(  );
        for (ReadQueryExecutionComplexityEstimationImpl.Type type : ReadQueryExecutionComplexityEstimationImpl.Type.values()){
            map.put( type, getDistribution( query, type ) );
        }
        return map;
    }

    @Override
    public void increment( ReadQuery query, ReadQueryExecutionComplexityEstimation readQueryExecutionComplexityEstimationImpl ) {
        for (ReadQueryExecutionComplexityEstimationImpl.Type type : ReadQueryExecutionComplexityEstimationImpl.Type.values()){
            incrementDistribution( query, type, type.retrieve( readQueryExecutionComplexityEstimationImpl ) );
        }
    }

    @Override
    public IncrementalContinuousDistribution incrementDistribution(Query query, ReadQueryExecutionComplexityEstimationImpl.Type type, double value){
        IncrementalContinuousDistribution distr = getDistribution( query, type );
        distr.increment( value );
        return distr;
    }

    @Override
    public IncrementalContinuousDistribution getDistribution(Query query, ReadQueryExecutionComplexityEstimationImpl.Type type ){
        return distributions.get( query.getClass() ).get( type );
    }

    @Resource(name = "perQueryDistributions")
    public void setDistributions( Map<Class<? extends Query>, Map<ReadQueryExecutionComplexityEstimationImpl.Type, IncrementalContinuousDistribution>> distributions ) {
        this.distributions = Collections.unmodifiableMap(distributions);
    }
}
