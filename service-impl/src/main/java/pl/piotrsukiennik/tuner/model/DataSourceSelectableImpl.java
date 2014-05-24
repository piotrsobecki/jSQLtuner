package pl.piotrsukiennik.tuner.model;

import pl.piotrsukiennik.ai.selectable.AbstractSelectable;
import pl.piotrsukiennik.tuner.model.datasource.DataSourceIdentity;
import pl.piotrsukiennik.tuner.service.DataSourceSelectable;

/**
 * @author Piotr Sukiennik
 * @date 13.02.14
 */
public class DataSourceSelectableImpl extends AbstractSelectable<DataSourceIdentifier> implements DataSourceSelectable<DataSourceIdentifier> {

    private double averageExecutionTime = 0;
    private double lastExecutionTime = 0;
    private long executions = 0;

    private double fitness = 0;

    public DataSourceSelectableImpl( DataSourceIdentity identifier ) {
        super( new DataSourceIdentifier( identifier ) );
    }

    public DataSourceSelectableImpl( ReadQueryExecutionComplexityEstimation readQueryExecutionComplexityEstimation,DataSourceIdentifier dataSourceIdentifier ) {
        super( dataSourceIdentifier );
        this.executions=1;
        this.lastExecutionTime=averageExecutionTime;
        this.averageExecutionTime= readQueryExecutionComplexityEstimation.getExecutionTimeNano();
        this.fitness=0;
    }

    @Override
    public void update( ReadQueryExecutionComplexityEstimation estimation ) {
        this.lastExecutionTime=estimation.getExecutionTimeNano();
        this.update();
    }

    protected void update( ) {
        this.averageExecutionTime = ( ( executions * averageExecutionTime ) + lastExecutionTime ) / ++executions;
    }

    @Override
    public DataSourceIdentity getDataSource() {
        return getIdentifier().getDataSourceIdentity();
    }

    public double getLastExecutionTime() {
        return lastExecutionTime;
    }

    @Override
    public double getFitness() {
        return fitness;
    }

    @Override
    public double getAverageExecutionTime() {
        return averageExecutionTime;
    }

    public long getExecutions() {
        return executions;
    }

    @Override
    public void setFitness( double fitness ) {
        this.fitness = fitness;
    }
}
