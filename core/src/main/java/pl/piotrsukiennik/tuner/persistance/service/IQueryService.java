package pl.piotrsukiennik.tuner.persistance.service;

import pl.piotrsukiennik.tuner.persistance.model.query.Query;
import pl.piotrsukiennik.tuner.persistance.model.query.condition.Condition;
import pl.piotrsukiennik.tuner.persistance.model.query.execution.DataSource;
import pl.piotrsukiennik.tuner.persistance.model.query.other.ColumnValue;
import pl.piotrsukiennik.tuner.persistance.model.query.other.GroupByFragment;
import pl.piotrsukiennik.tuner.persistance.model.query.other.OrderByFragment;
import pl.piotrsukiennik.tuner.persistance.model.query.other.Values;
import pl.piotrsukiennik.tuner.persistance.model.query.projection.Projection;
import pl.piotrsukiennik.tuner.persistance.model.query.source.Source;

/**
 * Author: Piotr Sukiennik
 * Date: 27.07.13
 * Time: 14:30
 */
public interface IQueryService {

    void submit(Query query);
    void submit(Condition condition);
    void submit(ColumnValue columnValue);
    void submit(GroupByFragment groupByFragment);
    void submit(OrderByFragment orderByFragment);
    void submit(Values values);
    void submit(Projection projection);
    void submit(Source source);

}
