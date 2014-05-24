package pl.piotrsukiennik.tuner.service;

import org.apache.commons.math.distribution.ContinuousDistribution;
import pl.piotrsukiennik.tuner.model.ReadQueryExecutionComplexityEstimation;
import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.statistics.CumulativeProbabilityCapable;

import java.util.Map;

/**
 * @author Piotr Sukiennik
 * @date 22.05.14
 */
public interface QueryExecutionComplexityStatisticsService {
    void increment(ReadQuery query, ReadQueryExecutionComplexityEstimation readQueryExecutionComplexityEstimation );
    Map<ReadQueryExecutionComplexityEstimation.Type,? extends CumulativeProbabilityCapable> getDistributions(ReadQuery query);

    ContinuousDistribution getDistribution(Query query, ReadQueryExecutionComplexityEstimation.Type type );
    ContinuousDistribution incrementDistribution(Query query, ReadQueryExecutionComplexityEstimation.Type type, double value);
}
