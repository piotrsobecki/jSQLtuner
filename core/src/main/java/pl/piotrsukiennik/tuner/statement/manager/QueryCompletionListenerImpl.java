package pl.piotrsukiennik.tuner.statement.manager;

import org.springframework.stereotype.Component;
import pl.piotrsukiennik.tuner.persistance.model.query.Query;
import pl.piotrsukiennik.tuner.persistance.service.IQueryService;
import pl.piotrsukiennik.tuner.statement.StatementHolder;
import pl.piotrsukiennik.tuner.util.ServicesHolder;

import javax.annotation.Resource;

/**
 * Author: Piotr Sukiennik
 * Date: 28.07.13
 * Time: 18:45
 */
@Component
public class QueryCompletionListenerImpl implements QueryCompletionListener {

    private @Resource ServicesHolder servicesHolder;

    @Override
    public void queryCompleted(Query query) {
        servicesHolder.getQueryService().submit(query);
    }

    @Override
    public Integer getOrder() {
        return Integer.MAX_VALUE;
    }
}
