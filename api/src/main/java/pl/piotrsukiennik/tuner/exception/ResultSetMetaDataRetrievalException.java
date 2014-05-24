package pl.piotrsukiennik.tuner.exception;

import pl.piotrsukiennik.tuner.model.query.ReadQuery;

/**
 * @author Piotr Sukiennik
 * @date 24.05.14
 */
public class ResultSetMetaDataRetrievalException extends AbstractQueryException {
    public ResultSetMetaDataRetrievalException( String message, Throwable cause, ReadQuery query ) {
        super( message, cause, query );
    }
}
