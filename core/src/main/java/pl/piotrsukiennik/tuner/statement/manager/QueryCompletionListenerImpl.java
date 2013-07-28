package pl.piotrsukiennik.tuner.statement.manager;

import org.springframework.stereotype.Component;
import pl.piotrsukiennik.tuner.persistance.model.query.Query;
import pl.piotrsukiennik.tuner.statement.StatementHolder;

/**
 * Author: Piotr Sukiennik
 * Date: 28.07.13
 * Time: 18:45
 */
@Component
public class QueryCompletionListenerImpl implements QueryCompletionListener {


    @Override
    public void queryCompleted(Query query) {
        query.setExecutionTimeMillis(System.currentTimeMillis() - query.getTimestamp());
        int a = 0;
    }

    @Override
    public Integer getOrder() {
        return Integer.MIN_VALUE;
    }
}
