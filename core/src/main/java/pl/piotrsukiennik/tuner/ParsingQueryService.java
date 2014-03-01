package pl.piotrsukiennik.tuner;

import pl.piotrsukiennik.tuner.exception.QueryParsingNotSupportedException;
import pl.piotrsukiennik.tuner.model.query.Query;

/**
 * @author Piotr Sukiennik
 * @date 01.03.14
 */
public interface ParsingQueryService {
    <T extends Query> T get( String database, String schema, String query ) throws QueryParsingNotSupportedException;
}
