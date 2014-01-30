package pl.piotrsukiennik.tuner.service;

import pl.piotrsukiennik.tuner.DataSource;
import pl.piotrsukiennik.tuner.dto.DataRetrieval;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.model.query.WriteQueryExecution;
import pl.piotrsukiennik.tuner.model.query.execution.QuerySelectionHelper;

import java.util.Collection;

/**
 * @author Piotr Sukiennik
 * @date 13.01.14
 */
public interface QueryDataSourceSelectorService {

    //AI get best for query
    DataSource selectDataSourceForQuery( ReadQuery selectQuery );

    //DataSourceIdentity will support query
    QuerySelectionHelper submit( ReadQuery query, DataSource dataSource );

    //Data Retrieval from query execution on datasource
    Collection<QuerySelectionHelper> submit( ReadQuery query, DataRetrieval dataRetrieval );

    //Submit data about execution
    void submit( WriteQueryExecution writeQueryExecution );

    void removeDataSourcesForQueries( Collection<? extends ReadQuery> queries, Collection<? extends DataSource> dataSources );

    void removeDataSourcesForQuery( ReadQuery query, Collection<? extends DataSource> dataSources );

    void removeDataSourceForQuery( ReadQuery query, DataSource dataSource );
}
