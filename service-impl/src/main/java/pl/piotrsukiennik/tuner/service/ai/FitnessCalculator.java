package pl.piotrsukiennik.tuner.service.ai;

/**
 * @author Piotr Sukiennik
 * @date 13.02.14
 */
public interface FitnessCalculator {
    double calc( DataSourceSelectable sourceForQuery );
}
