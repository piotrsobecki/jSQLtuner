package pl.piotrsukiennik.tuner.service;

import pl.piotrsukiennik.tuner.DataSource;
import pl.piotrsukiennik.tuner.datasource.RecommendationContext;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;

import java.util.Collection;

/**
 * @author Piotr Sukiennik
 * @date 22.05.14
 */
public interface DataSourceRecommendationService<RQ extends ReadQuery> {
    <DS extends DataSource> Collection<DS> possible(RecommendationContext<RQ,DS> context);
    <DS extends DataSource> boolean isShardable( RecommendationContext<RQ,DS> context );
}
