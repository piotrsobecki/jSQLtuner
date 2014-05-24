package pl.piotrsukiennik.tuner.model;

import pl.piotrsukiennik.tuner.model.datasource.DataSourceIdentity;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.statistics.CumulativeProbabilityCapable;

import java.util.Collection;
import java.util.Map;

/**
 * @author Piotr Sukiennik
 * @date 22.05.14
 */
public class DataSourceRecommendationContextImpl<RQ extends ReadQuery,DS extends DataSourceIdentity>implements DataSourceRecommendationContext<RQ,DS> {

    private Collection<DS> nodes;
    private RQ readQuery;
    private Map<ReadQueryExecutionComplexityEstimationImpl.Type,? extends CumulativeProbabilityCapable> queryDistributions;
    private ExecutionComplexityEstimation executionComplexityEstimation;


    DataSourceRecommendationContextImpl() {
    }

    void setNodes( Collection<DS> nodes ) {
        this.nodes = nodes;
    }

    void setReadQuery( RQ readQuery ) {
        this.readQuery = readQuery;
    }

    void setQueryDistributions( Map<ReadQueryExecutionComplexityEstimationImpl.Type, ? extends CumulativeProbabilityCapable> queryDistributions ) {
        this.queryDistributions = queryDistributions;
    }

    void setReadQueryExecutionComplexityEstimation( ExecutionComplexityEstimation executionComplexityEstimation ) {
        this.executionComplexityEstimation = executionComplexityEstimation;
    }

    @Override public Collection<DS> getNodes() {
        return nodes;
    }

    @Override public RQ getReadQuery() {
        return readQuery;
    }

    @Override public Map<ReadQueryExecutionComplexityEstimationImpl.Type, ? extends CumulativeProbabilityCapable> getQueryDistributions() {
        return queryDistributions;
    }

    @Override public ExecutionComplexityEstimation getReadQueryExecutionComplexityEstimation() {
        return executionComplexityEstimation;
    }
}
