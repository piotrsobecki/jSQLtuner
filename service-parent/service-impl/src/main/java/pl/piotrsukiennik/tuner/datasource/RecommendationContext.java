package pl.piotrsukiennik.tuner.datasource;

import org.apache.commons.math.distribution.ContinuousDistribution;
import pl.piotrsukiennik.tuner.DataSource;
import pl.piotrsukiennik.tuner.complexity.ComplexityEstimation;
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
    private Map<ComplexityEstimation.Type,? extends ContinuousDistribution> queryDistributions;
    private ComplexityEstimation complexityEstimation;

    public static class Builder<RQ extends ReadQuery,DS extends DataSourceIdentity> implements GenericBuilder<RecommendationContext>{

        private Collection<DS> nodes;
        private RQ readQuery;
        private Map<ComplexityEstimation.Type,? extends ContinuousDistribution> queryDistributions;
        private ComplexityEstimation complexityEstimation;

        public Builder<RQ,DS> withDataSources( Collection<DS> nodes ){
            this.nodes=nodes;
            return this;
        }

        public Builder<RQ,DS> withReadQuery( RQ readQuery ){
            this.readQuery=readQuery;
            return this;
        }

        public Builder<RQ,DS> withDistributions( Map<ComplexityEstimation.Type, ? extends ContinuousDistribution> queryDistributions ){
            this.queryDistributions=queryDistributions;
            return this;
        }

        public Builder<RQ,DS> withComplexityEstimation( ComplexityEstimation complexityEstimation ){
            this.complexityEstimation=complexityEstimation;
            return this;
        }

        @Override
        public RecommendationContext<RQ,DS> build() {
            RecommendationContext<RQ,DS> context = new RecommendationContext<RQ,DS>();
            context.nodes=nodes;
            context.readQuery=readQuery;
            context.queryDistributions=queryDistributions;
            context.complexityEstimation=complexityEstimation;
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

    public Map<ComplexityEstimation.Type, ? extends ContinuousDistribution> getQueryDistributions() {
        return queryDistributions;
    }

    public ComplexityEstimation getComplexityEstimation() {
        return complexityEstimation;
    }
}
