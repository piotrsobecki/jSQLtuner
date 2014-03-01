package pl.piotrsukiennik.tuner.persistance;

import pl.piotrsukiennik.tuner.model.log.WriteQueryExecution;
import pl.piotrsukiennik.tuner.model.query.Query;

/**
 * @author Piotr Sukiennik
 * @date 14.01.14
 */
public interface QueryDao {
    <T extends Query> T getQuery( String hash );

    <T extends Query> T getQuery( Class<? extends Query> clazz, String hash );

    <T extends Query> T createQuery( T query );

    void createQueryExecution( WriteQueryExecution writeQueryExecution );

}
