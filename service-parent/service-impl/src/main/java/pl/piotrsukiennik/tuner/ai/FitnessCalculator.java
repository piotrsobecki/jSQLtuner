package pl.piotrsukiennik.tuner.ai;

/**
 * @author Piotr Sukiennik
 * @date 13.02.14
 */
public interface FitnessCalculator {
    double calc( DataSourceSelectable sourceForQuery );
}
