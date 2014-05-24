package pl.piotrsukiennik.tuner.model;

import pl.piotrsukiennik.tuner.model.datasource.DataSourceIdentity;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.statistics.CumulativeProbabilityCapable;
import pl.piotrsukiennik.tuner.util.GenericBuilder;

import java.util.Collection;
import java.util.Map;

public class DataSourceRecommendationContextBuilder<RQ extends ReadQuery,DS extends DataSourceIdentity> implements GenericBuilder<DataSourceRecommendationContext>{

        private Collection<DS> nodes;
        private RQ readQuery;
        private Map<ReadQueryExecutionComplexityEstimationImpl.Type,? extends CumulativeProbabilityCapable> queryDistributions;
        private ExecutionComplexityEstimation executionComplexityEstimation;

        public DataSourceRecommendationContextBuilder<RQ,DS> withDataSources( Collection<DS> nodes ){
            this.nodes=nodes;
            return this;
        }

        public DataSourceRecommendationContextBuilder<RQ,DS> withReadQuery( RQ readQuery ){
            this.readQuery=readQuery;
            return this;
        }

        public DataSourceRecommendationContextBuilder<RQ,DS> withDistributions( Map<ReadQueryExecutionComplexityEstimationImpl.Type, ? extends CumulativeProbabilityCapable> queryDistributions ){
            this.queryDistributions=queryDistributions;
            return this;
        }

        public DataSourceRecommendationContextBuilder<RQ,DS> withComplexityEstimation( ExecutionComplexityEstimation executionComplexityEstimation ){
            this.executionComplexityEstimation = executionComplexityEstimation;
            return this;
        }

        @Override
        public DataSourceRecommendationContext<RQ,DS> build() {
            DataSourceRecommendationContextImpl<RQ,DS> context = new DataSourceRecommendationContextImpl<RQ,DS>();
            context.setNodes( nodes );
            context.setReadQuery(readQuery);
            context.setQueryDistributions(queryDistributions);
            context.setReadQueryExecutionComplexityEstimation( executionComplexityEstimation );
            return context;
        }
    }
