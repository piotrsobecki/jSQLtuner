package pl.piotrsukiennik.tuner.ai.impl;

import pl.piotrsukiennik.ai.selectable.AbstractSelectable;
import pl.piotrsukiennik.tuner.DataSource;
import pl.piotrsukiennik.tuner.ai.DataSourceSelectable;
import pl.piotrsukiennik.tuner.dto.DataRetrieval;
import pl.piotrsukiennik.tuner.model.datasource.DataSourceIdentity;

/**
 * @author Piotr Sukiennik
 * @date 13.02.14
 */
public class DataSourceSelectableImpl extends AbstractSelectable<DataSourceIdentifier> implements DataSourceSelectable<DataSourceIdentifier> {


    private DataSourceIdentity dataSource;

    private double averageExecutionTime = 0;

    private long executions = 0;

    private long rows = 0;

    private double fitness = 0;

    DataSourceSelectableImpl( DataSource dataSource ) {
        super( new DataSourceIdentifier( dataSource.getDataSourceIdentity() ) );
        this.dataSource = dataSource.getDataSourceIdentity();
    }

    DataSourceSelectableImpl( DataSourceIdentity dataSource ) {
        super( new DataSourceIdentifier( dataSource ) );
        this.dataSource = dataSource;
    }

    DataSourceSelectableImpl( DataRetrieval dataRetrieval ) {
        super( new DataSourceIdentifier( dataRetrieval.getDataSource() ) );
        this.dataSource = dataRetrieval.getDataSource();
        this.rows = dataRetrieval.getRows();
        this.updateExecutionTime( dataRetrieval.getExecutionTimeNano() );
    }

    @Override
    public void updateExecutionTime( double executionTime ) {
        this.averageExecutionTime = ( ( executions * averageExecutionTime ) + executionTime ) / ++executions;
    }


    @Override
    public DataSourceIdentity getDataSource() {
        return dataSource;
    }

    @Override
    public double getFitness() {
        return fitness;
    }

    @Override
    public long getRows() {
        return rows;
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
