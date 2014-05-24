package pl.piotrsukiennik.tuner.service;

import pl.piotrsukiennik.tuner.exception.DataRetrievalException;
import pl.piotrsukiennik.tuner.model.DataSource;
import pl.piotrsukiennik.tuner.model.ReadQueryExecutionResult;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;

/**
 * @author Piotr Sukiennik
 * @date 17.02.14
 */
public interface ReadQueryExecutionService {
    ReadQueryExecutionResult execute( DataSource dataSource, ReadQuery query ) throws DataRetrievalException;
}
