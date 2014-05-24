package pl.piotrsukiennik.tuner.exception;

import pl.piotrsukiennik.tuner.model.datasource.DataSourceIdentity;
import pl.piotrsukiennik.tuner.model.query.Query;

/**
 * @author Piotr Sukiennik
 * @date 22.01.14
 */
public class DataWriteException extends  RuntimeException {

    private DataSourceIdentity dataSourceIdentity;
    private Query query;

    public DataWriteException(  String message,  Query query, DataSourceIdentity dataSourceIdentity ) {
        super( message );
        this.query=query;
        this.dataSourceIdentity = dataSourceIdentity;
    }


}
