package pl.piotrsukiennik.tuner.persistance.service;

import pl.piotrsukiennik.tuner.persistance.model.query.ReadQuery;
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

    DataSource getDataSourceForQuery(ReadQuery selectQuery);
    DataSource getDataSource(Class clazz, String identifier);
    Collection<QueryForDataSource> submit(final ReadQuery query,final  DataSource dataSource, long executionTimeNano);
    QueryForDataSource submitNewDataSourceForQuery(final ReadQuery query,final  DataSource dataSource);
    void removeDataSourceForQuery(final ReadQuery query, final DataSource dataSource);
    void removeDataSourcesForQuery(final ReadQuery query, final Collection<DataSource> dataSource);
    void removeDataSourcesForQueries(Collection<? extends  ReadQuery> queries, final Collection<DataSource> dataSource);

}
