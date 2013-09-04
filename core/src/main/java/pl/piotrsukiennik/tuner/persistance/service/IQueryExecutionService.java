package pl.piotrsukiennik.tuner.persistance.service;

import pl.piotrsukiennik.tuner.persistance.model.query.SelectQuery;
import pl.piotrsukiennik.tuner.persistance.model.query.execution.DataSource;
import pl.piotrsukiennik.tuner.persistance.model.query.execution.QueryForDataSource;

import java.util.Collection;

/**
 * Author: Piotr Sukiennik
 * Date: 31.08.13
 * Time: 19:57
 */
public interface IQueryExecutionService {

    DataSource getDataSourceForQuery(SelectQuery selectQuery);
    DataSource getDataSource(Class clazz, String identifier);
    Collection<QueryForDataSource> submit(final SelectQuery query,final  DataSource dataSource, long executionTimeNano, long rows);
    QueryForDataSource submitNewDataSourceForQuery(final SelectQuery query,final  DataSource dataSource);
    void removeDataSourceForQuery(final SelectQuery query, final DataSource dataSource);

}
