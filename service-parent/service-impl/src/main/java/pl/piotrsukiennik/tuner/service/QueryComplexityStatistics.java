package pl.piotrsukiennik.tuner.service;

import org.apache.commons.math.distribution.ContinuousDistribution;
import pl.piotrsukiennik.tuner.complexity.ComplexityEstimation;
import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.statistics.IncrContDistr;

import java.util.Map;

/**
 * @author Piotr Sukiennik
 * @date 22.05.14
 */
public interface QueryComplexityStatistics {
    Map<ComplexityEstimation.Type,? extends ContinuousDistribution> increment(Query query, ComplexityEstimation complexityEstimation);
    Map<ComplexityEstimation.Type,? extends ContinuousDistribution> getDistributions(Query query);

    ContinuousDistribution getDistribution(Query query, ComplexityEstimation.Type type );
    ContinuousDistribution incrementDistribution(Query query, ComplexityEstimation.Type type, double value);
}
