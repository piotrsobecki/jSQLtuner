package pl.piotrsukiennik.tuner;

import pl.piotrsukiennik.tuner.dto.DataRetrieval;
import pl.piotrsukiennik.tuner.exception.DataRetrievalException;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;

/**
 * Author: Piotr Sukiennik
 * Date: 31.08.13
 * Time: 18:57
 */
public interface ShardService extends Shard {

    DataRetrieval get( ReadQuery query ) throws DataRetrievalException;

    void setDefaultDataSource( ReadQuery query, DataSource dataSource );

}
