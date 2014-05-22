package pl.piotrsukiennik.tuner.statistics;

import org.apache.commons.math.MathException;
import org.apache.commons.math.distribution.NormalDistribution;
import org.apache.commons.math.distribution.NormalDistributionImpl;
import org.apache.commons.math.stat.descriptive.moment.Mean;
import org.apache.commons.math.stat.descriptive.moment.StandardDeviation;

/**
 * @author Piotr Sukiennik
 * @date 22.05.14
 */
public class IncrContNormalDistrImpl implements IncrContDistr, NormalDistribution {

    private Mean mean = new Mean();

    private StandardDeviation standardDeviation = new StandardDeviation();

    private static final Object _lock = new Object();

    private NormalDistribution normalDistribution = new NormalDistributionImpl();

    @Override
    public void increment( double value ) {
        synchronized ( _lock ) {
            mean.increment( value );
            standardDeviation.increment( value );
            double meanD = mean.getResult();
            double sdD = standardDeviation.getResult();
            normalDistribution = new NormalDistributionImpl(
                 meanD,
                 sdD==0?1:sdD
            );
        }
    }

    @Override
    public double getStandardDeviation() {
        synchronized ( _lock ) {
            return normalDistribution.getStandardDeviation();
        }
    }

    @Override
    public double getMean() {
        synchronized ( _lock ) {
            return normalDistribution.getMean();
        }
    }

    @Override
    public double inverseCumulativeProbability( double v ) throws MathException {
        synchronized ( _lock ) {
            return normalDistribution.inverseCumulativeProbability( v );
        }
    }

    @Override
    public double cumulativeProbability( double v ) throws MathException {
        synchronized ( _lock ) {
            return normalDistribution.cumulativeProbability( v );
        }
    }

    @Override
    public double cumulativeProbability( double v, double v2 ) throws MathException {
        synchronized ( _lock ) {
            return normalDistribution.cumulativeProbability( v, v2 );
        }
    }

    @Override
    @Deprecated
    public void setStandardDeviation( double v ) {
        normalDistribution.setStandardDeviation( v );
    }

    @Override
    public double density( Double aDouble ) {
        synchronized ( _lock ) {
            return normalDistribution.density( aDouble );
        }
    }

    @Override
    @Deprecated
    public void setMean( double v ) {
        normalDistribution.setMean( v );
    }
}
