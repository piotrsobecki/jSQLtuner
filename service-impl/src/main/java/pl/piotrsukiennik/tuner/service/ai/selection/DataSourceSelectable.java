package pl.piotrsukiennik.tuner.service.ai.selection;

import pl.piotrsukiennik.ai.selectable.AbstractSelectable;
import pl.piotrsukiennik.tuner.model.query.datasource.DataSourceIdentity;

/**
 * @author Piotr Sukiennik
 * @date 13.02.14
 */
public class DataSourceSelectable extends AbstractSelectable<DataSourceIdentifier> {

    private DataSourceSelectionHelper selectionHelper;

    private DataSourceIdentity dataSource;

    private double averageExecutionTime = 0;

    private long executions = 0;

    private long rows = 0;

    private double fitness = 0;

    public DataSourceSelectable( DataSourceSelectionHelper selectionHelper, DataSourceIdentity dataSource ) {
        super( new DataSourceIdentifier( dataSource ) );
        this.selectionHelper = selectionHelper;
        this.dataSource = dataSource;
        this.selectionHelper.scheduleForSelection( this );

    }

    public DataSourceSelectable( DataSourceSelectionHelper selectionHelper, DataSourceIdentity dataSource, double executionTime, long rows ) {
        super( new DataSourceIdentifier( dataSource ) );
        this.selectionHelper = selectionHelper;
        this.dataSource = dataSource;
        this.rows = rows;
        this.updateExecutionTime( executionTime );
        this.selectionHelper.submit( this );
    }

    public void updateExecutionTime( double executionTime ) {
        this.averageExecutionTime = ( ( executions * averageExecutionTime ) + executionTime ) / ++executions;
        this.selectionHelper.updateFitness( this );
    }


    public DataSourceIdentity getDataSource() {
        return dataSource;
    }

    public double getFitness() {
        return fitness;
    }

    public long getRows() {
        return rows;
    }

    public double getAverageExecutionTime() {
        return averageExecutionTime;
    }

    void setFitness( double fitness ) {
        this.fitness = fitness;
    }
}
