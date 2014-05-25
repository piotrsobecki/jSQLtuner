package pl.piotrsukiennik.tuner.model;

import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;

import java.util.concurrent.TimeUnit;

/**
 * @author Piotr Sukiennik
 * @date 19.02.14
 */
public enum LoggableMessageEnum implements LoggableMessage {
    PARSING(
     "Query | parsing | took %d %s | String: %s",
     null
    ),
    EXECUTION(
     "Query | execution | took %d %s",
     "Query | execution | took %d %s | using [%s:%s] | String: %s"
    );

    protected String messageFormat;

    protected String messageFormatDataSource;

    LoggableMessageEnum( String messageFormat, String messageFormatDataSource ) {
        this.messageFormat = messageFormat;
        this.messageFormatDataSource = messageFormatDataSource;
    }

    public String getMessage( Query query, TimeUnit timeUnit, long duration ) {
        return String.format(
             messageFormat,
             duration,
             timeUnit.name(),
             query.getQueryString()
        );
    }

    public String getMessage(  QueryExecutionResult<ReadQuery,ExecutionComplexityEstimation> readQueryExecutionResult ) {
        long executionTimeNano = readQueryExecutionResult.getReadQueryExecutionComplexityEstimation().getExecutionTimeNano();
        return String.format(
             messageFormatDataSource,
             executionTimeNano,
             TimeUnit.NANOSECONDS.name(),
             readQueryExecutionResult.getDataSource().getClazz(),
             readQueryExecutionResult.getDataSource().getIdentifier(),
             readQueryExecutionResult.getQuery().getQueryString()
        );
    }
}
