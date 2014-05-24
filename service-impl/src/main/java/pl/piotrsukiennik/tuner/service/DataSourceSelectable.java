package pl.piotrsukiennik.tuner.service;

import pl.piotrsukiennik.ai.id.Identifier;
import pl.piotrsukiennik.ai.selectable.Selectable;
import pl.piotrsukiennik.tuner.model.ReadQueryExecutionComplexityEstimation;
import pl.piotrsukiennik.tuner.model.datasource.DataSourceIdentity;

/**
 * @author Piotr Sukiennik
 * @date 17.02.14
 */
public interface DataSourceSelectable<I extends Identifier> extends Selectable<I> {
    void update( ReadQueryExecutionComplexityEstimation estimation );

    DataSourceIdentity getDataSource();

    void setFitness( double fitness );
    long getExecutions();
    double getLastExecutionTime();

    double getAverageExecutionTime();
}
