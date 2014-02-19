package pl.piotrsukiennik.tuner.service;

import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.model.query.impl.SelectQuery;

import java.util.Collection;

/**
 * @author Piotr Sukiennik
 * @date 14.01.14
 */
public interface CacheService {

    void putCachedQuery( SelectQuery query );

    Collection<SelectQuery> getQueriesToInvalidate( Query query );
}
