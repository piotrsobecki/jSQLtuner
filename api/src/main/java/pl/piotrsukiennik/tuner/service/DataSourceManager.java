package pl.piotrsukiennik.tuner.service;

import pl.piotrsukiennik.tuner.exception.DataRetrievalException;
import pl.piotrsukiennik.tuner.model.DataSource;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;

import java.sql.ResultSet;

/**
 * Author: Piotr Sukiennik
 * Date: 31.08.13
 * Time: 18:57
 */
public interface DataSourceManager extends DataSource {
    ResultSet execute( DataSource defaultDataSource,ReadQuery query ) throws DataRetrievalException;
}
