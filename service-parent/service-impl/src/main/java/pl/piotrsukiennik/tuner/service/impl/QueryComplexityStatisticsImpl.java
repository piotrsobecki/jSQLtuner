package pl.piotrsukiennik.tuner.service.impl;

import org.apache.commons.math.distribution.ContinuousDistribution;
import org.springframework.stereotype.Service;
import pl.piotrsukiennik.tuner.complexity.ComplexityEstimation;
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

    private Map<Class<? extends Query>, Map<ComplexityEstimation.Type,IncrContDistr>> distributions;

    @Override
    public Map<ComplexityEstimation.Type, ? extends ContinuousDistribution> getDistributions( Query query ) {
        Map<ComplexityEstimation.Type,IncrContDistr> map = new HashMap<>(  );
        for (ComplexityEstimation.Type type : ComplexityEstimation.Type.values()){
            map.put( type, getDistribution( query, type ) );
        }
        return map;
    }

    @Override
    public Map<ComplexityEstimation.Type,IncrContDistr> increment( Query query, ComplexityEstimation complexityEstimation ) {
        Map<ComplexityEstimation.Type,IncrContDistr> map = new HashMap<>(  );
        for (ComplexityEstimation.Type type : ComplexityEstimation.Type.values()){
            map.put( type, incrementDistribution( query, type, type.retrieve( complexityEstimation ) ) );
        }
        return map;
    }

    @Override
    public IncrContDistr incrementDistribution(Query query, ComplexityEstimation.Type type, double value){
        IncrContDistr distr = getDistribution( query, type );
        distr.increment( value );
        return distr;
    }

    @Override
    public IncrContDistr getDistribution(Query query, ComplexityEstimation.Type type ){
        return distributions.get( query.getClass() ).get( type );
    }

    @Resource(name = "perQueryDistributions")
    public void setDistributions( Map<Class<? extends Query>, Map<ComplexityEstimation.Type, IncrContDistr>> distributions ) {
        this.distributions = Collections.unmodifiableMap(distributions);
    }
}
