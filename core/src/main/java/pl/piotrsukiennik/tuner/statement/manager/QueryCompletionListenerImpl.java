package pl.piotrsukiennik.tuner.statement.manager;

import org.springframework.stereotype.Component;
import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.model.query.WriteQuery;
import pl.piotrsukiennik.tuner.persistance.DaoHolder;

/**
 * Author: Piotr Sukiennik
 * Date: 28.07.13
 * Time: 18:45
 */
@Component
public class QueryCompletionListenerImpl implements QueryCompletionListener {


    @Override
    public void queryCompleted( Query query ) {
        if ( query instanceof WriteQuery ) {
            DaoHolder.getQueryDao().submit( query );
        }
    }

    @Override
    public Integer getOrder() {
        return Integer.MAX_VALUE;
    }
}
