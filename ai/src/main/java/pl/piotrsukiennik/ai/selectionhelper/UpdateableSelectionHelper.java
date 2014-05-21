package pl.piotrsukiennik.ai.selectionhelper;

import pl.piotrsukiennik.ai.id.Identifier;
import pl.piotrsukiennik.ai.selectable.Selectable;

import java.util.Collection;

/**
 * @author Piotr Sukiennik
 * @date 12.02.14
 */
public interface UpdateableSelectionHelper<T extends Selectable> extends SelectionHelper<T> {
    boolean update( T t );

    boolean removeOption( T value );

    boolean removeOption( Identifier optionIdentifier );

    Collection<T> getOptions();
}
