package pl.piotrsukiennik.tuner.model;

import pl.piotrsukiennik.tuner.model.datasource.DataSourceIdentity;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.statistics.CumulativeProbabilityCapable;

import java.util.Collection;
import java.util.Map;

/**
 * @author Piotr Sukiennik
 * @date 24.05.14
 */
public interface DataSourceRecommendationContext<RQ extends ReadQuery, DS extends DataSourceIdentity> {
    Collection<DS> getNodes();

    RQ getReadQuery();

    Map<ReadQueryExecutionComplexityEstimation.Type, ? extends CumulativeProbabilityCapable> getQueryDistributions();

    ExecutionComplexityEstimation getReadQueryExecutionComplexityEstimation();
}
