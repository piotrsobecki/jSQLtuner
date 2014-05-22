package pl.piotrsukiennik.tuner.ai;

import pl.piotrsukiennik.ai.selectionhelper.UpdateableSelectionHelper;
import pl.piotrsukiennik.tuner.dto.ReadQueryExecutionResult;

/**
 * @author Piotr Sukiennik
 * @date 17.02.14
 */
public interface DataSourceSelectionHelper<T extends DataSourceSelectable> extends UpdateableSelectionHelper<T> {

    void submit( ReadQueryExecutionResult readQueryExecutionResult,T selectable );

    void schedule( T selectable );
}
