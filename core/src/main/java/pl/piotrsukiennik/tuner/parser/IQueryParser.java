package pl.piotrsukiennik.tuner.parser;


/**
 * Author: Piotr Sukiennik
 * Date: 30.06.13
 * Time: 17:44
 */
public interface IQueryParser {
    IQuery parse(String query);
}
