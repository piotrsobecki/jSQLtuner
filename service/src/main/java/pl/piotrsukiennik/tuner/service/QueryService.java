package pl.piotrsukiennik.tuner.service;

import pl.piotrsukiennik.tuner.exception.QueryParsingNotSupportedException;
import pl.piotrsukiennik.tuner.model.query.Query;

/**
 * @author Piotr Sukiennik
 * @date 19.02.14
 */
public interface QueryService {
    String getHash( String database, String schema, String query );

    <T extends Query> T get( String hash );

    <T extends Query> T get( String database, String schema, String query ) throws QueryParsingNotSupportedException;

    <T extends Query> void save( T query, String database, String schema, String queryStr );

    <T extends Query> void save( T query, String hash );
}
