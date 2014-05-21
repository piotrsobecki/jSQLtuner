package pl.piotrsukiennik.tuner.cache;

/**
 * @author Piotr Sukiennik
 * @date 28.02.14
 */
public interface QueryInvalidating {
    <R> R invalidates( QueryInvalidaton<R> invalidator );
}
