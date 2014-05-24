package pl.piotrsukiennik.tuner.service.impl;

import org.apache.commons.math.distribution.ContinuousDistribution;
import org.springframework.stereotype.Service;
import pl.piotrsukiennik.tuner.dto.ReadQueryExecutionComplexityEstimation;
import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.service.QueryExecutionComplexityStatisticsService;
import pl.piotrsukiennik.tuner.statistics.IncrContDistr;

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

    private Map<Class<? extends Query>, Map<ReadQueryExecutionComplexityEstimation.Type,IncrContDistr>> distributions;

    @Override
    public Map<ReadQueryExecutionComplexityEstimation.Type, ? extends ContinuousDistribution> getDistributions( ReadQuery query ) {
        Map<ReadQueryExecutionComplexityEstimation.Type,IncrContDistr> map = new HashMap<>(  );
        for (ReadQueryExecutionComplexityEstimation.Type type : ReadQueryExecutionComplexityEstimation.Type.values()){
            map.put( type, getDistribution( query, type ) );
        }
        return map;
    }

    @Override
    public void increment( ReadQuery query, ReadQueryExecutionComplexityEstimation readQueryExecutionComplexityEstimation ) {
        for (ReadQueryExecutionComplexityEstimation.Type type : ReadQueryExecutionComplexityEstimation.Type.values()){
            incrementDistribution( query, type, type.retrieve( readQueryExecutionComplexityEstimation ) );
        }
    }

    @Override
    public IncrContDistr incrementDistribution(Query query, ReadQueryExecutionComplexityEstimation.Type type, double value){
        IncrContDistr distr = getDistribution( query, type );
        distr.increment( value );
        return distr;
    }

    @Override
    public IncrContDistr getDistribution(Query query, ReadQueryExecutionComplexityEstimation.Type type ){
        return distributions.get( query.getClass() ).get( type );
    }

    @Resource(name = "perQueryDistributions")
    public void setDistributions( Map<Class<? extends Query>, Map<ReadQueryExecutionComplexityEstimation.Type, IncrContDistr>> distributions ) {
        this.distributions = Collections.unmodifiableMap(distributions);
    }
}
