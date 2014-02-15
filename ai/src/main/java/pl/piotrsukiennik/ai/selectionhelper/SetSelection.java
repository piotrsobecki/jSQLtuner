package pl.piotrsukiennik.ai.selectionhelper;

import pl.piotrsukiennik.ai.selectable.Selectable;

import java.util.Collection;

/**
 * @author Piotr Sukiennik
 * @date 13.02.14
 */
public abstract class SetSelection<T extends Selectable> extends CollectionSelection<T> implements UpdateableSelectionHelper<T> {

    protected abstract Collection<T> getCollection();

    public synchronized boolean fitnessChanged( T value ) {
        return getCollection().remove( value ) && getCollection().add( value );
    }


}
