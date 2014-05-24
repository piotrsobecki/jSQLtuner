package pl.piotrsukiennik.tuner.service;

import pl.piotrsukiennik.tuner.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.model.schema.Column;
import pl.piotrsukiennik.tuner.model.schema.Table;

import java.util.Collection;

/**
 * @author Piotr Sukiennik
 * @date 16.02.14
 */
public interface ReadQueryTreeService<K extends ReadQuery> {

    Collection<K> getReadQueries( Table table );

    Collection<K> getReadQueries( Column column );

    Collection<K> getReadQueriesByStar( Table table );

    void submitReadQuery( K query );

}
