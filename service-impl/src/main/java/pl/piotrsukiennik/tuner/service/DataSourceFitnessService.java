package pl.piotrsukiennik.tuner.service;

/**
 * @author Piotr Sukiennik
 * @date 13.02.14
 */
public interface DataSourceFitnessService {
    double calc( DataSourceSelectable sourceForQuery );
}
