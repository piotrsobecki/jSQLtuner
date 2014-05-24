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
     "Query (%s) parsing took %d %s.",
     null
    ),
    EXECUTION(
     "Query (%s) execution took %d %s.",
     "Query (%s) execution took %d %s, using (%s:%s)"
    );

    protected String messageFormat;

    protected String messageFormatDataSource;

    LoggableMessageEnum( String messageFormat, String messageFormatDataSource ) {
        this.messageFormat = messageFormat;
        this.messageFormatDataSource = messageFormatDataSource;
    }

    public String getMessage( Query query, TimeUnit timeUnit, long duration ) {
        return String.format( messageFormat, query.getQueryString(), duration, timeUnit.name() );
    }

    public String getMessage(  QueryExecutionResult<ReadQuery,ExecutionComplexityEstimation> readQueryExecutionResult ) {
        long executionTimeNano = readQueryExecutionResult.getReadQueryExecutionComplexityEstimation().getExecutionTimeNano();
        return String.format( messageFormatDataSource, readQueryExecutionResult.getQuery().getQueryString(), executionTimeNano, TimeUnit.NANOSECONDS.name(), readQueryExecutionResult.getDataSource().getClazz(), readQueryExecutionResult.getDataSource().getIdentifier() );
    }
}
