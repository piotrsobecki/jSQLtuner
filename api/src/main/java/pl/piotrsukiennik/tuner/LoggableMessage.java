package pl.piotrsukiennik.tuner;

import pl.piotrsukiennik.tuner.dto.DataRetrieval;
import pl.piotrsukiennik.tuner.model.query.Query;

import java.util.concurrent.TimeUnit;

/**
 * @author Piotr Sukiennik
 * @date 19.02.14
 */
public interface LoggableMessage {
    String getMessage( Query query, TimeUnit timeUnit, long duration );

    String getMessage( DataRetrieval dataRetrieval );
}
