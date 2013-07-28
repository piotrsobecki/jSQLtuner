package pl.piotrsukiennik.tuner.statement.manager;

import org.springframework.stereotype.Component;
import pl.piotrsukiennik.tuner.statement.StatementHolder;

import java.sql.Statement;

/**
 * Author: Piotr Sukiennik
 * Date: 28.07.13
 * Time: 18:08
 */

public interface StatementLiveCycleListener {
     void onNewStatement(StatementHolder statementHolder);
     void onRemoveStatement(StatementHolder statementHolder);
}
