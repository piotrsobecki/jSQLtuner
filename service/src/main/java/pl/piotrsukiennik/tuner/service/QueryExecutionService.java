package pl.piotrsukiennik.tuner.service;

import pl.piotrsukiennik.tuner.IDataSource;
import pl.piotrsukiennik.tuner.dto.DataRetrieval;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.model.query.WriteQueryExecution;
import pl.piotrsukiennik.tuner.model.query.execution.QueryForDataSource;

import java.util.Collection;

/**
 * @author Piotr Sukiennik
 * @date 13.01.14
 */
public interface QueryExecutionService {
    void removePossibleDataSources( Collection<? extends ReadQuery> queries,
                                    Collection<? extends IDataSource> dataSources );

    void removePossibleDataSources( ReadQuery query, Collection<? extends IDataSource> dataSources );

    void removePossibleDataSource( ReadQuery query, IDataSource dataSource );

    QueryForDataSource submitPossibleDataSource( ReadQuery query,
                                                 IDataSource dataSource );

    Collection<QueryForDataSource> submit( ReadQuery query,
                                           DataRetrieval dataRetrieval );

    IDataSource getDataSourceForQuery( ReadQuery selectQuery );


    void submitWriteQueryExecution( WriteQueryExecution writeQueryExecution );
}
