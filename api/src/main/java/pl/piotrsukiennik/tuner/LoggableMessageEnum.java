package pl.piotrsukiennik.tuner;

import pl.piotrsukiennik.tuner.dto.DataRetrieval;
import pl.piotrsukiennik.tuner.model.query.Query;

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
        return String.format( messageFormat, query, duration, timeUnit.name() );
    }

    public String getMessage( DataRetrieval dataRetrieval ) {
        return String.format( messageFormatDataSource, dataRetrieval.getReadQuery(), dataRetrieval.getExecutionTimeNano(), TimeUnit.NANOSECONDS.name(), dataRetrieval.getDataSourceIdentity().getClazz(), dataRetrieval.getDataSourceIdentity().getIdentifier() );
    }
}
