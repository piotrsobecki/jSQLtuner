package pl.piotrsukiennik.tuner.service;

import pl.piotrsukiennik.tuner.cache.QueryInvalidator;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;

import java.util.Collection;

/**
 * @author Piotr Sukiennik
 * @date 14.01.14
 */
public interface QueryInvalidatorService extends QueryInvalidator<Collection<ReadQuery>> {
    void putCachedQuery( ReadQuery query );
}
