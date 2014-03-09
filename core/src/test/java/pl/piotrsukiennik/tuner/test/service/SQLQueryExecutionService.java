package pl.piotrsukiennik.tuner.test.service;

import java.util.List;

/**
 * @author Piotr Sukiennik
 * @date 09.03.14
 */
public interface SQLQueryExecutionService {
    List execute( String query );
}
