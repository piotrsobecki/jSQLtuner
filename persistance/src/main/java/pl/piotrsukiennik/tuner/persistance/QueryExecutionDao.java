package pl.piotrsukiennik.tuner.persistance;

import pl.piotrsukiennik.tuner.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.model.query.execution.DataSourceIdentity;
import pl.piotrsukiennik.tuner.model.query.execution.QuerySelectionHelper;

import java.util.Collection;
import java.util.List;

/**
 * Author: Piotr Sukiennik
 * Date: 31.08.13
 * Time: 19:57
 */
public interface QueryExecutionDao {

    DataSourceIdentity getDataSourceForQuery( ReadQuery selectQuery );

    List<DataSourceIdentity> getDataSourcesForQuery( ReadQuery selectQuery );

    DataSourceIdentity getDataSource( String identifier );

    Collection<QuerySelectionHelper> submit( final ReadQuery query, final DataSourceIdentity dataSourceIdentity, long executionTimeNano );

    QuerySelectionHelper submitNewDataSourceForQuery( final ReadQuery query, final DataSourceIdentity dataSourceIdentity );

    void removeDataSourceForQuery( final ReadQuery query, final DataSourceIdentity dataSourceIdentity );

    void removeDataSourcesForQuery( final ReadQuery query, final Collection<DataSourceIdentity> dataSourceIdentity );

    void removeDataSourcesForQueries( Collection<? extends ReadQuery> queries, final Collection<DataSourceIdentity> dataSourceIdentity );

}
