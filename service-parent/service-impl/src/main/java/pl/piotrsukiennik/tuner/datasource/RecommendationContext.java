package pl.piotrsukiennik.tuner.datasource;

import org.apache.commons.math.distribution.ContinuousDistribution;
import pl.piotrsukiennik.tuner.dto.QueryComplexityEstimation;
import pl.piotrsukiennik.tuner.model.datasource.DataSourceIdentity;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.util.GenericBuilder;

import java.util.Collection;
import java.util.Map;

/**
 * @author Piotr Sukiennik
 * @date 22.05.14
 */
public class RecommendationContext<RQ extends ReadQuery,DS extends DataSourceIdentity> {

    private Collection<DS> nodes;
    private RQ readQuery;
    private Map<QueryComplexityEstimation.Type,? extends ContinuousDistribution> queryDistributions;
    private QueryComplexityEstimation queryComplexityEstimation;

    public static class Builder<RQ extends ReadQuery,DS extends DataSourceIdentity> implements GenericBuilder<RecommendationContext>{

        private Collection<DS> nodes;
        private RQ readQuery;
        private Map<QueryComplexityEstimation.Type,? extends ContinuousDistribution> queryDistributions;
        private QueryComplexityEstimation queryComplexityEstimation;

        public Builder<RQ,DS> withDataSources( Collection<DS> nodes ){
            this.nodes=nodes;
            return this;
        }

        public Builder<RQ,DS> withReadQuery( RQ readQuery ){
            this.readQuery=readQuery;
            return this;
        }

        public Builder<RQ,DS> withDistributions( Map<QueryComplexityEstimation.Type, ? extends ContinuousDistribution> queryDistributions ){
            this.queryDistributions=queryDistributions;
            return this;
        }

        public Builder<RQ,DS> withComplexityEstimation( QueryComplexityEstimation queryComplexityEstimation ){
            this.queryComplexityEstimation = queryComplexityEstimation;
            return this;
        }

        @Override
        public RecommendationContext<RQ,DS> build() {
            RecommendationContext<RQ,DS> context = new RecommendationContext<RQ,DS>();
            context.nodes=nodes;
            context.readQuery=readQuery;
            context.queryDistributions=queryDistributions;
            context.queryComplexityEstimation = queryComplexityEstimation;
            return context;
        }
    }

    private RecommendationContext() {
    }

    public Collection<DS> getNodes() {
        return nodes;
    }

    public RQ getReadQuery() {
        return readQuery;
    }

    public Map<QueryComplexityEstimation.Type, ? extends ContinuousDistribution> getQueryDistributions() {
        return queryDistributions;
    }

    public QueryComplexityEstimation getQueryComplexityEstimation() {
        return queryComplexityEstimation;
    }
}
