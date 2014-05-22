package pl.piotrsukiennik.tuner.service;

import pl.piotrsukiennik.tuner.DataSource;
import pl.piotrsukiennik.tuner.dto.ReadQueryExecutionResult;
import pl.piotrsukiennik.tuner.exception.DataRetrievalException;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;

/**
 * @author Piotr Sukiennik
 * @date 17.02.14
 */
public interface DataRetrievalService {
    ReadQueryExecutionResult get( DataSource dataSource, ReadQuery query ) throws DataRetrievalException;
}
