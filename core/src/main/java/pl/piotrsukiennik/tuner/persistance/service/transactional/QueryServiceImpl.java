package pl.piotrsukiennik.tuner.persistance.service.transactional;

import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.piotrsukiennik.tuner.persistance.model.query.Query;
import pl.piotrsukiennik.tuner.persistance.model.query.condition.Condition;
import pl.piotrsukiennik.tuner.persistance.model.query.other.ColumnValue;
import pl.piotrsukiennik.tuner.persistance.model.query.other.GroupByFragment;
import pl.piotrsukiennik.tuner.persistance.model.query.other.OrderByFragment;
import pl.piotrsukiennik.tuner.persistance.model.query.other.Values;
import pl.piotrsukiennik.tuner.persistance.model.query.projection.Projection;
import pl.piotrsukiennik.tuner.persistance.model.query.source.Source;
import pl.piotrsukiennik.tuner.persistance.service.AbstractService;
import pl.piotrsukiennik.tuner.persistance.service.IQueryService;

/**
 * Author: Piotr Sukiennik
 * Date: 12.08.13
 * Time: 15:31
 */
@Service(value = "QueryService")
@Transactional(readOnly = false)
public class QueryServiceImpl extends AbstractService implements IQueryService {

    @Override
    public void submit(Query query) {
        persist(query);
    }

    @Override
    public void submit(Condition condition) {
        persist(condition);
    }

    @Override
    public void submit(ColumnValue columnValue) {
        persist(columnValue);
    }

    @Override
    public void submit(GroupByFragment groupByFragment) {
        persist(groupByFragment);
    }

    @Override
    public void submit(OrderByFragment orderByFragment) {
        persist(orderByFragment);
    }

    @Override
    public void submit(Values values) {
        persist(values);
    }

    @Override
    public void submit(Projection projection) {
        persist(projection);
    }

    @Override
    public void submit(Source source) {
        persist(source);
    }

    protected void persist(Object object){
        Session session = s();
        session.save(object);
        session.flush();
        session.refresh(object);
    }
}

