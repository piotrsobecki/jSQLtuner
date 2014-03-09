package pl.piotrsukiennik.tuner.persistence.impl;

import org.springframework.stereotype.Repository;
import pl.piotrsukiennik.tuner.model.log.QueryException;
import pl.piotrsukiennik.tuner.persistence.LogDao;

import java.sql.Timestamp;

/**
 * Author: Piotr Sukiennik
 * Date: 27.07.13
 * Time: 15:01
 */
@Repository
class LogDaoImpl extends CrudDaoImpl implements LogDao {
    @Override
    public void log( String query, Throwable exception ) {
        String exceptionMessage = null;
        if ( exception.getCause() == null ) {
            exceptionMessage = exception.getMessage();
        }
        else {
            exceptionMessage = exception.getCause().getMessage();
        }
        log( query, exceptionMessage );
    }

    @Override
    public void log( String query, String exception ) {
        QueryException queryException = new QueryException();
        queryException.setValue( query );
        queryException.setTimestamp( new Timestamp( System.currentTimeMillis() ) );
        queryException.setMessage( exception );
        create( queryException );
    }


}
