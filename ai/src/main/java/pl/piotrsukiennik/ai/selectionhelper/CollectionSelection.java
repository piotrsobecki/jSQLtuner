package pl.piotrsukiennik.ai.selectionhelper;

import pl.piotrsukiennik.ai.id.Identifier;
import pl.piotrsukiennik.ai.selectable.Selectable;

import java.util.Collection;

/**
 * @author Piotr Sukiennik
 * @date 13.02.14
 */
public abstract class CollectionSelection<T extends Selectable> implements UpdateableSelectionHelper<T> {

    protected abstract Collection<T> getCollection();

    @Override
    public T getSelection( Identifier identifier ) {
        for ( T sel : getCollection() ) {
            if ( identifier.equals( sel.getIdentifier() ) ) {
                return sel;
            }
        }
        return null;
    }

    @Override
    public boolean removeOption( T value ) {
        return getCollection().remove( value );
    }

    @Override
    public boolean removeOption( Identifier optionIdentifier ) {
        T toRemove = null;
        for ( T t : getCollection() ) {
            if ( t.getIdentifier().equals( optionIdentifier ) ) {
                toRemove = t;
                break;
            }
        }
        return removeOption( toRemove );
    }


}
