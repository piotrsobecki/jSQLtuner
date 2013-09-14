package pl.piotrsukiennik.tuner.test.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.piotrsukiennik.tuner.test.model.Test;

import javax.annotation.Resource;
import javax.inject.Named;
import java.util.Collections;
import java.util.List;

/**
 * Author: Piotr Sukiennik
 * Date: 08.07.13
 * Time: 17:49
 */
@Service
@Transactional
public class SQLQueryExecutionService extends AbstractService {
    public List execute(String query){
        if (query.toLowerCase().startsWith("select")){
            return s().createSQLQuery(query).list();
        } else {
            s().createSQLQuery(query).executeUpdate();
            return Collections.EMPTY_LIST;
        }
    }
}
