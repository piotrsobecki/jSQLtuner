package pl.piotrsukiennik.tuner.model;

import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;

import java.util.concurrent.TimeUnit;

/**
 * @author Piotr Sukiennik
 * @date 19.02.14
 */
public interface LoggableMessage {
    String getMessage( Query query, TimeUnit timeUnit, long duration );

    String getMessage( QueryExecutionResult<ReadQuery,ExecutionComplexityEstimation> readQueryExecutionResult );
}
