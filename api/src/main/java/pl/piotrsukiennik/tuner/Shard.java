package pl.piotrsukiennik.tuner;

import pl.piotrsukiennik.tuner.dto.ReadQueryExecutionResult;
import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;

/**
 * Author: Piotr Sukiennik
 * Date: 31.08.13
 * Time: 18:57
 */
public interface Shard {
    boolean contains( ReadQuery query );

    void put( ReadQueryExecutionResult data  );

    void delete( Query query );
}
