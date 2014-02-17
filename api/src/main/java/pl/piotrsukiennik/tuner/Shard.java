package pl.piotrsukiennik.tuner;

import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;

import javax.sql.rowset.CachedRowSet;

/**
 * Author: Piotr Sukiennik
 * Date: 31.08.13
 * Time: 18:57
 */
public interface Shard {
    boolean contains( ReadQuery query );

    void put( ReadQuery query, CachedRowSet data );

    void delete( Query query );
}
