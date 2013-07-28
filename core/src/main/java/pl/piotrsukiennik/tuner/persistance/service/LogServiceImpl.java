package pl.piotrsukiennik.tuner.persistance.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.piotrsukiennik.tuner.persistance.model.log.QueryException;

import java.sql.Timestamp;

/**
 * Author: Piotr Sukiennik
 * Date: 27.07.13
 * Time: 15:01
 */
@Service
@Transactional(readOnly = true)
public class LogServiceImpl extends AbstractService implements ILogService{
    @Override
    @Transactional(readOnly = false)
    public void logException(String query, Exception exception) {
        QueryException queryException = new QueryException();
        queryException.setValue(query);
        queryException.setTimestamp(new Timestamp(System.currentTimeMillis()));
        queryException.setMessage(exception.getMessage());
        s().save(queryException);
    }
}
