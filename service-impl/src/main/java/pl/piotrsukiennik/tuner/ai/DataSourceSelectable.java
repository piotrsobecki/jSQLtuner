package pl.piotrsukiennik.tuner.ai;

import pl.piotrsukiennik.ai.id.Identifier;
import pl.piotrsukiennik.ai.selectable.Selectable;
import pl.piotrsukiennik.tuner.model.query.datasource.DataSourceIdentity;

/**
 * @author Piotr Sukiennik
 * @date 17.02.14
 */
public interface DataSourceSelectable<I extends Identifier> extends Selectable<I> {
    void updateExecutionTime( double executionTime );

    DataSourceIdentity getDataSource();

    void setFitness( double fitness );

    long getRows();

    double getAverageExecutionTime();
}
