package pl.piotrsukiennik.tuner.persistance.service.transactional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.piotrsukiennik.tuner.persistance.model.log.QueryException;
import pl.piotrsukiennik.tuner.persistance.service.AbstractService;
import pl.piotrsukiennik.tuner.persistance.service.ILogService;

import java.sql.Timestamp;

/**
 * Author: Piotr Sukiennik
 * Date: 27.07.13
 * Time: 15:01
 */
@Service(value = "LogService")
@Transactional(readOnly = true)
public class LogServiceImpl extends AbstractService implements ILogService {

    public void logException(String query, Throwable exception) {
        logException(query,exception.getCause().getMessage());
    }
    @Override
    @Transactional(readOnly = false)
    public void logException(String query, String exception) {
        QueryException queryException = new QueryException();
        queryException.setValue(query);
        queryException.setTimestamp(new Timestamp(System.currentTimeMillis()));
        queryException.setMessage(exception);
        s().save(queryException);
    }
}
