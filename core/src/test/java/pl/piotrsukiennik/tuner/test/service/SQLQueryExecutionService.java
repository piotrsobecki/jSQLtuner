package pl.piotrsukiennik.tuner.test.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * Author: Piotr Sukiennik
 * Date: 08.07.13
 * Time: 17:49
 */
@Service
@Transactional("transactionManager")
public class SQLQueryExecutionService extends AbstractService {
    public List execute( String query ) {
        if ( query.toLowerCase().startsWith( "select" ) ) {
            return s().createSQLQuery( query ).list();
        }
        else {
            int rowsUpdated = s().createSQLQuery( query ).executeUpdate();
            return Collections.EMPTY_LIST;
        }
    }
}
