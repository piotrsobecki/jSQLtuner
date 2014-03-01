package pl.piotrsukiennik.tuner;

import pl.piotrsukiennik.tuner.exception.QueryParsingNotSupportedException;
import pl.piotrsukiennik.tuner.model.query.Query;

/**
 * @author Piotr Sukiennik
 * @date 28.02.14
 */
public interface QueryProvider {
    <T extends Query> T getQuery( String database, String schema, String query ) throws QueryParsingNotSupportedException;
}
