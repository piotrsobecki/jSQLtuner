package pl.piotrsukiennik.tuner.service.ai.fitness;

import pl.piotrsukiennik.tuner.service.ai.selection.DataSourceSelectable;

/**
 * @author Piotr Sukiennik
 * @date 13.02.14
 */
public interface FitnessCalculator {
    double calc( DataSourceSelectable sourceForQuery );
}
