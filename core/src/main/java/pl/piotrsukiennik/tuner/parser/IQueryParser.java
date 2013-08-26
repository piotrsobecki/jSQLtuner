package pl.piotrsukiennik.tuner.parser;


import pl.piotrsukiennik.tuner.persistance.model.query.Query;

/**
 * Author: Piotr Sukiennik
 * Date: 30.06.13
 * Time: 17:44
 */
public interface IQueryParser {
    Query parse(String databaseStr, String schemaStr, String queryStr)  ;
}
