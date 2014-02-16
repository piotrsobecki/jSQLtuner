package pl.piotrsukiennik.tuner.service.cache;

import java.util.Collection;

/**
 * @author Piotr Sukiennik
 * @date 16.02.14
 */
public interface HolderFactory<T extends Collection> {
    <Q> Holder<Q> build();
}
