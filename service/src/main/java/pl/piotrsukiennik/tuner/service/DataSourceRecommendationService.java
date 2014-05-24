package pl.piotrsukiennik.tuner.service;

import pl.piotrsukiennik.tuner.model.datasource.DataSourceIdentity;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;

import java.util.Collection;

/**
 * @author Piotr Sukiennik
 * @date 22.05.14
 */
public interface DataSourceRecommendationService<RQ extends ReadQuery> {
    <DS extends DataSourceIdentity> Collection<DS> possible(DataSourceRecommendationContext<RQ,DS> context);
    <DS extends DataSourceIdentity> boolean isShardable( DataSourceRecommendationContext<RQ,DS> context );
}
