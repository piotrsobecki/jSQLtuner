package pl.piotrsukiennik.tuner.persistance.service.transactional;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.piotrsukiennik.tuner.persistance.model.ValueEntity;
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
    public <T extends Query> T submit(T query) {
        if (query.getId()==0){
            T queryPersisted = (T)getQueryByHash(query.getHash());
            if (queryPersisted==null){
               return persist(query);
            } else{
                return queryPersisted;
            }
        } else {
            return query;
        }
    }

    protected Query getQueryByHash(String hash){
        return (Query)s().createCriteria(Query.class).add(Restrictions.eq("hash",hash)).setMaxResults(1).uniqueResult();
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

    protected <T extends ValueEntity> T persist(T object){
        Session session = s();
        session.save(object);
        session.flush();
        session.refresh(object);
        return object;
    }
}

