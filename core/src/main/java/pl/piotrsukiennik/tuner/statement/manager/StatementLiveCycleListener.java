package pl.piotrsukiennik.tuner.statement.manager;

import pl.piotrsukiennik.tuner.statement.StatementHolder;

/**
 * Author: Piotr Sukiennik
 * Date: 28.07.13
 * Time: 18:08
 */

public interface StatementLiveCycleListener {
    void onNewStatement( StatementHolder statementHolder );

    void onRemoveStatement( StatementHolder statementHolder );
}
