package pl.piotrsukiennik.tuner.service;

import pl.piotrsukiennik.tuner.exception.QueryParsingNotSupportedException;
import pl.piotrsukiennik.tuner.model.log.WriteQueryExecution;
import pl.piotrsukiennik.tuner.model.query.Query;

/**
 * @author Piotr Sukiennik
 * @date 19.02.14
 */
public interface QueryService {

    <T extends Query> T getQuery( String database, String schema, String query ) throws QueryParsingNotSupportedException;

    <T extends Query> void createQuery( T query, String database, String schema, String queryStr );

    void createExecution( WriteQueryExecution writeQueryExecution );
}
