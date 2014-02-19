package pl.piotrsukiennik.tuner.util;

import java.util.concurrent.TimeUnit;

/**
 * @author Piotr Sukiennik
 * @date 19.02.14
 */
public interface Timed {
    long getTime( TimeUnit timeUnit );
}
