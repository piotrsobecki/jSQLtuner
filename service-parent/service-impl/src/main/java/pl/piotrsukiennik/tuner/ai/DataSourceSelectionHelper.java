package pl.piotrsukiennik.tuner.ai;

import pl.piotrsukiennik.ai.selectionhelper.UpdateableSelectionHelper;

/**
 * @author Piotr Sukiennik
 * @date 17.02.14
 */
public interface DataSourceSelectionHelper<T extends DataSourceSelectable> extends UpdateableSelectionHelper<T> {

    void schedule( T selectable );
}
