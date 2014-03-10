package pl.piotrsukiennik.tuner.service.impl.cache;

import java.util.Collection;

/**
 * @author Piotr Sukiennik
 * @date 16.02.14
 */
public interface HolderEnterableFactory<T extends Collection> extends HolderFactory<T> {
    <P, Q extends Holder> HolderEnterable<P, Q> buildEnterable();
}
