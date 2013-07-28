package pl.piotrsukiennik.tuner.statement.manager;

import pl.piotrsukiennik.tuner.persistance.model.query.Query;
import pl.piotrsukiennik.tuner.util.Ordered;

import java.sql.Statement;

/**
 * Author: Piotr Sukiennik
 * Date: 28.07.13
 * Time: 18:42
 */
public interface QueryInitializationListener  extends Ordered {
    void onNewQuery(Query query, Statement statement);


}
