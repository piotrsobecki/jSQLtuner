package pl.piotrsukiennik.tuner.service.impl;

import org.apache.commons.math.distribution.ContinuousDistribution;
import pl.piotrsukiennik.tuner.dto.ReadQueryExecutionComplexityEstimation;
import pl.piotrsukiennik.tuner.model.datasource.DataSourceIdentity;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.service.DataSourceRecommendationContext;

import java.util.Collection;
import java.util.Map;

/**
 * @author Piotr Sukiennik
 * @date 22.05.14
 */
public class DataSourceRecommendationContextImpl<RQ extends ReadQuery,DS extends DataSourceIdentity>implements DataSourceRecommendationContext<RQ,DS> {

    private Collection<DS> nodes;
    private RQ readQuery;
    private Map<ReadQueryExecutionComplexityEstimation.Type,? extends ContinuousDistribution> queryDistributions;
    private ReadQueryExecutionComplexityEstimation readQueryExecutionComplexityEstimation;


    DataSourceRecommendationContextImpl() {
    }

    void setNodes( Collection<DS> nodes ) {
        this.nodes = nodes;
    }

    void setReadQuery( RQ readQuery ) {
        this.readQuery = readQuery;
    }

    void setQueryDistributions( Map<ReadQueryExecutionComplexityEstimation.Type, ? extends ContinuousDistribution> queryDistributions ) {
        this.queryDistributions = queryDistributions;
    }

    void setReadQueryExecutionComplexityEstimation( ReadQueryExecutionComplexityEstimation readQueryExecutionComplexityEstimation ) {
        this.readQueryExecutionComplexityEstimation = readQueryExecutionComplexityEstimation;
    }

    @Override public Collection<DS> getNodes() {
        return nodes;
    }

    @Override public RQ getReadQuery() {
        return readQuery;
    }

    @Override public Map<ReadQueryExecutionComplexityEstimation.Type, ? extends ContinuousDistribution> getQueryDistributions() {
        return queryDistributions;
    }

    @Override public ReadQueryExecutionComplexityEstimation getReadQueryExecutionComplexityEstimation() {
        return readQueryExecutionComplexityEstimation;
    }
}
