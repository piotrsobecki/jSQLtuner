package pl.piotrsukiennik.tuner.cache;

/**
 * @author Piotr Sukiennik
 * @date 28.02.14
 */
public interface QueryInvalidation {
    <R> R accept( QueryInvalidatonVisitor<R> invalidator );
}
