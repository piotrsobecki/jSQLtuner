package pl.piotrsukiennik.tuner.service;

import pl.piotrsukiennik.tuner.model.query.Query;

/**
 * Author: Piotr Sukiennik
 * Date: 30.06.13
 * Time: 17:44
 */
public interface QueryParserService {
    Query parse( String databaseStr, String schemaStr, String queryStr );
}
