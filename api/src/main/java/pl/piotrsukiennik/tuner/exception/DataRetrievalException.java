package pl.piotrsukiennik.tuner.exception;

import pl.piotrsukiennik.tuner.model.datasource.DataSourceIdentity;
import pl.piotrsukiennik.tuner.model.query.Query;

/**
 * @author Piotr Sukiennik
 * @date 22.01.14
 */
public class DataRetrievalException extends AbstractQueryException {

    private DataSourceIdentity dataSourceIdentity;

    public DataRetrievalException( String query, DataSourceIdentity dataSourceIdentity ) {
        super( query );
        this.dataSourceIdentity = dataSourceIdentity;
    }

    public DataRetrievalException( String message, String query, DataSourceIdentity dataSourceIdentity ) {
        super( message, query );
        this.dataSourceIdentity = dataSourceIdentity;
    }

    public DataRetrievalException( String message, Throwable cause, String query, DataSourceIdentity dataSourceIdentity ) {
        super( message, cause, query );
        this.dataSourceIdentity = dataSourceIdentity;
    }

    public DataRetrievalException( String message, Throwable cause, String query ) {
        super( message, cause, query );
    }

    public DataRetrievalException( Throwable cause, String query, DataSourceIdentity dataSourceIdentity ) {
        super( cause, query );
        this.dataSourceIdentity = dataSourceIdentity;
    }

    public DataRetrievalException( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String query, DataSourceIdentity dataSourceIdentity ) {
        super( message, cause, enableSuppression, writableStackTrace, query );
        this.dataSourceIdentity = dataSourceIdentity;
    }

    public DataRetrievalException( Query query, DataSourceIdentity dataSourceIdentity ) {
        super( query );
        this.dataSourceIdentity = dataSourceIdentity;
    }

    public DataRetrievalException( String message, Query query, DataSourceIdentity dataSourceIdentity ) {
        super( message, query );
        this.dataSourceIdentity = dataSourceIdentity;
    }

    public DataRetrievalException( String message, Throwable cause, Query query, DataSourceIdentity dataSourceIdentity ) {
        super( message, cause, query );
        this.dataSourceIdentity = dataSourceIdentity;
    }

    public DataRetrievalException( Throwable cause, Query query, DataSourceIdentity dataSourceIdentity ) {
        super( cause, query );
        this.dataSourceIdentity = dataSourceIdentity;
    }

    public DataRetrievalException( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Query query, DataSourceIdentity dataSourceIdentity ) {
        super( message, cause, enableSuppression, writableStackTrace, query );
        this.dataSourceIdentity = dataSourceIdentity;
    }


}
