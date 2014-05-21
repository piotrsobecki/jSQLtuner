package pl.piotrsukiennik.ai.selectionhelper;

import pl.piotrsukiennik.ai.id.Identifier;
import pl.piotrsukiennik.ai.selectable.Selectable;

import java.util.Collection;

/**
 * @author Piotr Sukiennik
 * @date 12.02.14
 */
public interface SelectionHelper<T extends Selectable> {
    T select();

    void submit( T selectable );

    T getSelection( Identifier identifier );
}
