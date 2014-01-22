package pl.piotrsukiennik.tuner.service.cache.model;

import pl.piotrsukiennik.tuner.service.util.Objects2;

import java.util.Collection;

/**
 * Author: Piotr Sukiennik
 * Date: 12.09.13
 * Time: 22:43
 */
public class EnterableHolderFactory<T extends Collection> extends HolderFactory<T> {

    public EnterableHolderFactory( Class<T> holerCollection ) {
        super( holerCollection );

    }

    public <P, Q extends Holder> EnterableHolder<P, Q> buildEnterable() {
        return new EnterableHolderImpl<P, Q>( Objects2.newInstance( holerCollection ) );
    }
}
