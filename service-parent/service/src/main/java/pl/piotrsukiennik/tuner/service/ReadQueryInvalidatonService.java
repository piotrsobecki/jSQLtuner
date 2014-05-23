package pl.piotrsukiennik.tuner.service;

import pl.piotrsukiennik.tuner.cache.QueryInvalidaton;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;

import java.util.Collection;

/**
 * @author Piotr Sukiennik
 * @date 14.01.14
 */
public interface ReadQueryInvalidatonService extends QueryInvalidaton<Collection<ReadQuery>> {
    void putCachedQuery( ReadQuery query );
}
