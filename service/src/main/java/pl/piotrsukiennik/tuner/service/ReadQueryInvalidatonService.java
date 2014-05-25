package pl.piotrsukiennik.tuner.service;

import pl.piotrsukiennik.tuner.cache.QueryInvalidatonVisitor;
import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;

import java.util.Collection;

/**
 * @author Piotr Sukiennik
 * @date 14.01.14
 */
public interface ReadQueryInvalidatonService<Q extends ReadQuery> extends QueryInvalidatonVisitor<Collection<Q>> {
    void submitInvalidatableReadQuery( Q query );
    Collection<Q> getQueriesInvalidatedBy(Query query);
}
