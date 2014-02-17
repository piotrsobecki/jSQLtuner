package pl.piotrsukiennik.tuner.service.ai.impl;

import pl.piotrsukiennik.ai.selectable.AbstractSelectable;
import pl.piotrsukiennik.tuner.model.query.datasource.DataSourceIdentity;
import pl.piotrsukiennik.tuner.service.ai.DataSourceSelectable;
import pl.piotrsukiennik.tuner.service.ai.DataSourceSelectionHelper;

/**
 * @author Piotr Sukiennik
 * @date 13.02.14
 */
public class DataSourceSelectableImpl extends AbstractSelectable<DataSourceIdentifier> implements DataSourceSelectable<DataSourceIdentifier> {

    private DataSourceSelectionHelper selectionHelper;

    private DataSourceIdentity dataSource;

    private double averageExecutionTime = 0;

    private long executions = 0;

    private long rows = 0;

    private double fitness = 0;

    public DataSourceSelectableImpl( DataSourceSelectionHelper selectionHelper, DataSourceIdentity dataSource ) {
        super( new DataSourceIdentifier( dataSource ) );
        this.selectionHelper = selectionHelper;
        this.dataSource = dataSource;
        this.selectionHelper.scheduleForSelection( this );

    }

    public DataSourceSelectableImpl( DataSourceSelectionHelper selectionHelper, DataSourceIdentity dataSource, double executionTime, long rows ) {
        super( new DataSourceIdentifier( dataSource ) );
        this.selectionHelper = selectionHelper;
        this.dataSource = dataSource;
        this.rows = rows;
        this.updateExecutionTime( executionTime );
        this.selectionHelper.submit( this );
    }

    @Override
    public void updateExecutionTime( double executionTime ) {
        this.averageExecutionTime = ( ( executions * averageExecutionTime ) + executionTime ) / ++executions;
        this.selectionHelper.updateFitness( this );
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
