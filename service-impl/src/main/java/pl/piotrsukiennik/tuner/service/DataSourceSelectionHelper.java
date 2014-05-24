package pl.piotrsukiennik.tuner.service;

import pl.piotrsukiennik.ai.selectionhelper.UpdateableSelectionHelper;
import pl.piotrsukiennik.tuner.model.ReadQueryExecutionResult;

/**
 * @author Piotr Sukiennik
 * @date 17.02.14
 */
public interface DataSourceSelectionHelper<T extends DataSourceSelectable> extends UpdateableSelectionHelper<T> {

    void submit( ReadQueryExecutionResult readQueryExecutionResult,T selectable );

    void schedule( T selectable );
}
