package pl.piotrsukiennik.tuner.service.impl;

import org.apache.commons.math.distribution.ContinuousDistribution;
import org.springframework.stereotype.Service;
import pl.piotrsukiennik.tuner.dto.QueryComplexityEstimation;
import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.service.QueryComplexityStatistics;
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
public class QueryComplexityStatisticsImpl implements QueryComplexityStatistics {

    private Map<Class<? extends Query>, Map<QueryComplexityEstimation.Type,IncrContDistr>> distributions;

    @Override
    public Map<QueryComplexityEstimation.Type, ? extends ContinuousDistribution> getDistributions( Query query ) {
        Map<QueryComplexityEstimation.Type,IncrContDistr> map = new HashMap<>(  );
        for (QueryComplexityEstimation.Type type : QueryComplexityEstimation.Type.values()){
            map.put( type, getDistribution( query, type ) );
        }
        return map;
    }

    @Override
    public void increment( Query query, QueryComplexityEstimation queryComplexityEstimation ) {
        for (QueryComplexityEstimation.Type type : QueryComplexityEstimation.Type.values()){
            incrementDistribution( query, type, type.retrieve( queryComplexityEstimation ) );
        }
    }

    @Override
    public IncrContDistr incrementDistribution(Query query, QueryComplexityEstimation.Type type, double value){
        IncrContDistr distr = getDistribution( query, type );
        distr.increment( value );
        return distr;
    }

    @Override
    public IncrContDistr getDistribution(Query query, QueryComplexityEstimation.Type type ){
        return distributions.get( query.getClass() ).get( type );
    }

    @Resource(name = "perQueryDistributions")
    public void setDistributions( Map<Class<? extends Query>, Map<QueryComplexityEstimation.Type, IncrContDistr>> distributions ) {
        this.distributions = Collections.unmodifiableMap(distributions);
    }
}
