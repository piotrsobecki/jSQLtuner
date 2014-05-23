package pl.piotrsukiennik.tuner.service;

import org.apache.commons.math.distribution.ContinuousDistribution;
import pl.piotrsukiennik.tuner.dto.QueryComplexityEstimation;
import pl.piotrsukiennik.tuner.model.query.Query;

import java.util.Map;

/**
 * @author Piotr Sukiennik
 * @date 22.05.14
 */
public interface QueryComplexityStatistics {
    void increment(Query query, QueryComplexityEstimation queryComplexityEstimation );
    Map<QueryComplexityEstimation.Type,? extends ContinuousDistribution> getDistributions(Query query);

    ContinuousDistribution getDistribution(Query query, QueryComplexityEstimation.Type type );
    ContinuousDistribution incrementDistribution(Query query, QueryComplexityEstimation.Type type, double value);
}
