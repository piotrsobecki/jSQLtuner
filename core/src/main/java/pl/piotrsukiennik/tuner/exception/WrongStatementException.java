package pl.piotrsukiennik.tuner.exception;

import pl.piotrsukiennik.tuner.model.datasource.DataSourceIdentity;
import pl.piotrsukiennik.tuner.model.query.Query;

/**
 * @author Piotr Sukiennik
 * @date 22.01.14
 */
public class WrongStatementException extends DataRetrievalException {
    public WrongStatementException( String query, DataSourceIdentity dataSourceIdentity ) {
        super( query, dataSourceIdentity );
    }

    public WrongStatementException( String message, String query, DataSourceIdentity dataSourceIdentity ) {
        super( message, query, dataSourceIdentity );
    }

    public WrongStatementException( String message, Throwable cause, String query, DataSourceIdentity dataSourceIdentity ) {
        super( message, cause, query, dataSourceIdentity );
    }

    public WrongStatementException( Throwable cause, String query, DataSourceIdentity dataSourceIdentity ) {
        super( cause, query, dataSourceIdentity );
    }

    public WrongStatementException( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String query, DataSourceIdentity dataSourceIdentity ) {
        super( message, cause, enableSuppression, writableStackTrace, query, dataSourceIdentity );
    }

    public WrongStatementException( Query query, DataSourceIdentity dataSourceIdentity ) {
        super( query, dataSourceIdentity );
    }

    public WrongStatementException( String message, Query query, DataSourceIdentity dataSourceIdentity ) {
        super( message, query, dataSourceIdentity );
    }

    public WrongStatementException( String message, Throwable cause, Query query, DataSourceIdentity dataSourceIdentity ) {
        super( message, cause, query, dataSourceIdentity );
    }

    public WrongStatementException( Throwable cause, Query query, DataSourceIdentity dataSourceIdentity ) {
        super( cause, query, dataSourceIdentity );
    }

    public WrongStatementException( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Query query, DataSourceIdentity dataSourceIdentity ) {
        super( message, cause, enableSuppression, writableStackTrace, query, dataSourceIdentity );
    }
}
