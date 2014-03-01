package pl.piotrsukiennik.parser;

import pl.piotrsukiennik.tuner.exception.QueryParsingNotSupportedException;
import pl.piotrsukiennik.tuner.model.query.Query;

/**
 * Author: Piotr Sukiennik
 * Date: 30.06.13
 * Time: 17:44
 */
public interface Parser {
    <T extends Query> T parse( String databaseStr, String schemaStr, String queryStr ) throws QueryParsingNotSupportedException;
}
