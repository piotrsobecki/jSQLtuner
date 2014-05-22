package pl.piotrsukiennik.tuner.statistics;

import org.apache.commons.math.distribution.ContinuousDistribution;

/**
 * @author Piotr Sukiennik
 * @date 22.05.14
 */
public interface IncrContDistr extends ContinuousDistribution{
    void increment( double value );
}
