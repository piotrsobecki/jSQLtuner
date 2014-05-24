package pl.piotrsukiennik.tuner.service;

import org.apache.commons.math.distribution.ContinuousDistribution;
import pl.piotrsukiennik.tuner.dto.ReadQueryExecutionComplexityEstimation;
import pl.piotrsukiennik.tuner.model.datasource.DataSourceIdentity;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;

import java.util.Collection;
import java.util.Map;

/**
 * @author Piotr Sukiennik
 * @date 24.05.14
 */
public interface DataSourceRecommendationContext<RQ extends ReadQuery, DS extends DataSourceIdentity> {
    Collection<DS> getNodes();

    RQ getReadQuery();

    Map<ReadQueryExecutionComplexityEstimation.Type, ? extends ContinuousDistribution> getQueryDistributions();

    ReadQueryExecutionComplexityEstimation getReadQueryExecutionComplexityEstimation();
}
