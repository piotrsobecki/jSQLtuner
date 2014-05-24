package pl.piotrsukiennik.tuner.ai.impl;

import pl.piotrsukiennik.ai.selectable.AbstractSelectable;
import pl.piotrsukiennik.tuner.ai.DataSourceSelectable;
import pl.piotrsukiennik.tuner.dto.ReadQueryExecutionComplexityEstimation;
import pl.piotrsukiennik.tuner.model.datasource.DataSourceIdentity;

/**
 * @author Piotr Sukiennik
 * @date 13.02.14
 */
public class DataSourceSelectableImpl extends AbstractSelectable<DataSourceIdentifier> implements DataSourceSelectable<DataSourceIdentifier> {

    private double averageExecutionTime = 0;

    private long executions = 0;

    private double fitness = 0;

    public DataSourceSelectableImpl( DataSourceIdentity identifier ) {
        super( new DataSourceIdentifier( identifier ) );
    }

    public DataSourceSelectableImpl( ReadQueryExecutionComplexityEstimation readQueryExecutionComplexityEstimation,DataSourceIdentifier dataSourceIdentifier ) {
        super( dataSourceIdentifier );
        this.executions=1;
        this.averageExecutionTime= readQueryExecutionComplexityEstimation.getExecutionTimeNano();
        this.fitness=0;
    }

    @Override
    public void update( ReadQueryExecutionComplexityEstimation estimation ) {
        this.update( estimation.getExecutionTimeNano() );
    }

    protected void update( double executionTime ) {
        this.averageExecutionTime = ( ( executions * averageExecutionTime ) + executionTime ) / ++executions;
    }

    @Override
    public DataSourceIdentity getDataSource() {
        return getIdentifier().getDataSourceIdentity();
    }

    @Override
    public double getFitness() {
        return fitness;
    }

    @Override
    public double getAverageExecutionTime() {
        return averageExecutionTime;
    }

    @Override
    public void setFitness( double fitness ) {
        this.fitness = fitness;
    }
}
