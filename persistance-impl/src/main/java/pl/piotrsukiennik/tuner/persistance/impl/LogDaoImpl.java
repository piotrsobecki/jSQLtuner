package pl.piotrsukiennik.tuner.persistance.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.piotrsukiennik.tuner.model.log.QueryException;
import pl.piotrsukiennik.tuner.persistance.LogDao;

import java.sql.Timestamp;

/**
 * Author: Piotr Sukiennik
 * Date: 27.07.13
 * Time: 15:01
 */
@Repository
@Transactional(value = "jsqlTunerTransactionManager")
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
