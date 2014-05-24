package pl.piotrsukiennik.tuner.service;

import pl.piotrsukiennik.tuner.exception.DataRetrievalException;
import pl.piotrsukiennik.tuner.exception.QueryInterceptionNotSupportedException;
import pl.piotrsukiennik.tuner.exception.QueryParsingNotSupportedException;
import pl.piotrsukiennik.tuner.model.LoggableMessage;
import pl.piotrsukiennik.tuner.model.ReadQueryExecutionResult;
import pl.piotrsukiennik.tuner.model.query.Query;

import java.util.concurrent.TimeUnit;

/**
 * @author Piotr Sukiennik
 * @date 19.02.14
 */
public interface LoggableService {
    void log( DataRetrievalException exception );

    void log( QueryInterceptionNotSupportedException exception );

    void log( QueryParsingNotSupportedException exception );

    void log( ReadQueryExecutionResult readQueryExecutionResult );

    void log( Query query, LoggableMessage loggableMessage, TimeUnit timeUnit, long duration );
}
