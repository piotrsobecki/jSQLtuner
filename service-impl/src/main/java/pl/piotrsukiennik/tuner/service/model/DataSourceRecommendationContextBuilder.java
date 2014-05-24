package pl.piotrsukiennik.tuner.service.model;

import org.apache.commons.math.distribution.ContinuousDistribution;
import pl.piotrsukiennik.tuner.dto.ReadQueryExecutionComplexityEstimation;
import pl.piotrsukiennik.tuner.model.datasource.DataSourceIdentity;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.service.DataSourceRecommendationContext;
import pl.piotrsukiennik.tuner.util.GenericBuilder;

import java.util.Collection;
import java.util.Map;

public class DataSourceRecommendationContextBuilder<RQ extends ReadQuery,DS extends DataSourceIdentity> implements GenericBuilder<DataSourceRecommendationContext>{

        private Collection<DS> nodes;
        private RQ readQuery;
        private Map<ReadQueryExecutionComplexityEstimation.Type,? extends ContinuousDistribution> queryDistributions;
        private ReadQueryExecutionComplexityEstimation readQueryExecutionComplexityEstimation;

        public DataSourceRecommendationContextBuilder<RQ,DS> withDataSources( Collection<DS> nodes ){
            this.nodes=nodes;
            return this;
        }

        public DataSourceRecommendationContextBuilder<RQ,DS> withReadQuery( RQ readQuery ){
            this.readQuery=readQuery;
            return this;
        }

        public DataSourceRecommendationContextBuilder<RQ,DS> withDistributions( Map<ReadQueryExecutionComplexityEstimation.Type, ? extends ContinuousDistribution> queryDistributions ){
            this.queryDistributions=queryDistributions;
            return this;
        }

        public DataSourceRecommendationContextBuilder<RQ,DS> withComplexityEstimation( ReadQueryExecutionComplexityEstimation readQueryExecutionComplexityEstimation ){
            this.readQueryExecutionComplexityEstimation = readQueryExecutionComplexityEstimation;
            return this;
        }

        @Override
        public DataSourceRecommendationContext<RQ,DS> build() {
            DataSourceRecommendationContextImpl<RQ,DS> context = new DataSourceRecommendationContextImpl<RQ,DS>();
            context.setNodes( nodes );
            context.setReadQuery(readQuery);
            context.setQueryDistributions(queryDistributions);
            context.setReadQueryExecutionComplexityEstimation(readQueryExecutionComplexityEstimation);
            return context;
        }
    }
