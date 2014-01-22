package pl.piotrsukiennik.tuner.persistance;

import pl.piotrsukiennik.tuner.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.model.query.execution.DataSource;
import pl.piotrsukiennik.tuner.model.query.execution.QueryForDataSource;

import java.util.Collection;

/**
 * Author: Piotr Sukiennik
 * Date: 31.08.13
 * Time: 19:57
 */
public interface QueryExecutionDao {

    DataSource getDataSourceForQuery( ReadQuery selectQuery );

    DataSource getDataSource( String identifier );

    Collection<QueryForDataSource> submit( final ReadQuery query, final DataSource dataSource, long executionTimeNano );

    QueryForDataSource submitNewDataSourceForQuery( final ReadQuery query, final DataSource dataSource );

    void removeDataSourceForQuery( final ReadQuery query, final DataSource dataSource );

    void removeDataSourcesForQuery( final ReadQuery query, final Collection<DataSource> dataSource );

    void removeDataSourcesForQueries( Collection<? extends ReadQuery> queries, final Collection<DataSource> dataSource );

}
