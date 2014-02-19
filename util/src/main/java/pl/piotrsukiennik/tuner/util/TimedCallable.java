package pl.piotrsukiennik.tuner.util;

import java.util.concurrent.Callable;

/**
 * @author Piotr Sukiennik
 * @date 19.02.14
 */
public interface TimedCallable<V> extends Callable<V>, Timed {
}
