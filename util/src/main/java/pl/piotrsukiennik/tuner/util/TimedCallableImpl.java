package pl.piotrsukiennik.tuner.util;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * @author Piotr Sukiennik
 * @date 19.02.14
 */
public class TimedCallableImpl<V> implements TimedCallable<V> {

    private Callable<V> callable;

    long execTimeNano;

    public TimedCallableImpl( Callable<V> callable ) {
        this.callable = callable;
    }

    @Override
    public V call() throws Exception {
        long start = System.nanoTime();
        V out = callable.call();
        execTimeNano = System.nanoTime() - start;
        return out;
    }

    @Override
    public long getTime( TimeUnit timeUnit ) {
        return timeUnit.convert( execTimeNano, TimeUnit.NANOSECONDS );
    }
}
