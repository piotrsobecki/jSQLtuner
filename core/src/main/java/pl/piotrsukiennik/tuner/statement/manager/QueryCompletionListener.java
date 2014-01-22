package pl.piotrsukiennik.tuner.statement.manager;

import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.utils.Ordered;

/**
 * Author: Piotr Sukiennik
 * Date: 28.07.13
 * Time: 18:42
 */
public interface QueryCompletionListener extends Ordered {
    void queryCompleted( Query query );
}
