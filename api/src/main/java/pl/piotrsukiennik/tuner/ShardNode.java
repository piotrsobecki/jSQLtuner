package pl.piotrsukiennik.tuner;

import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;

import java.util.Collection;

/**
 * Author: Piotr Sukiennik
 * Date: 31.08.13
 * Time: 18:57
 */
public interface ShardNode extends DataSource, Shard {
    void delete( Query query, Collection<ReadQuery> queriesToInvalidate );

}
