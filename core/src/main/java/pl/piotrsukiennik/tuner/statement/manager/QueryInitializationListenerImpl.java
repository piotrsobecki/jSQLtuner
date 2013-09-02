package pl.piotrsukiennik.tuner.statement.manager;

import org.springframework.stereotype.Component;
import pl.piotrsukiennik.tuner.persistance.model.query.Query;
import pl.piotrsukiennik.tuner.util.holder.Holder;

import javax.annotation.Resource;
import java.sql.Statement;

/**
 * Author: Piotr Sukiennik
 * Date: 12.08.13
 * Time: 15:28
 */
@Component
public class QueryInitializationListenerImpl implements QueryInitializationListener {

    private @Resource
    Holder servicesHolder;

    @Override
    public void onNewQuery(Query query, Statement statement) {

    }

    @Override
    public Integer getOrder() {
        return 0;
    }
}
