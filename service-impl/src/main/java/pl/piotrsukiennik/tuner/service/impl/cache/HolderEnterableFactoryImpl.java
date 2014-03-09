package pl.piotrsukiennik.tuner.service.impl.cache;

import pl.piotrsukiennik.tuner.util.Objects2;

import java.util.Collection;

/**
 * Author: Piotr Sukiennik
 * Date: 12.09.13
 * Time: 22:43
 */
public class HolderEnterableFactoryImpl<T extends Collection> extends HolderFactoryImpl<T> implements HolderEnterableFactory<T> {

    public HolderEnterableFactoryImpl( Class<T> holerCollection ) {
        super( holerCollection );
    }

    @Override
    public <P, Q extends Holder> HolderEnterable<P, Q> buildEnterable() {
        return new HolderEnterableImpl<P, Q>( Objects2.newInstance( holerCollection ) );
    }
}
