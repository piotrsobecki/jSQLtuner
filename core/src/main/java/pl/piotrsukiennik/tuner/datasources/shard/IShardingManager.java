package pl.piotrsukiennik.tuner.datasources.shard;

import pl.piotrsukiennik.tuner.datasources.DataRetrieval;
import pl.piotrsukiennik.tuner.persistance.model.query.Query;
import pl.piotrsukiennik.tuner.persistance.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.persistance.model.query.SelectQuery;

import javax.sql.rowset.CachedRowSet;
import java.io.Serializable;
import java.sql.ResultSet;

/**
 * Author: Piotr Sukiennik
 * Date: 31.08.13
 * Time: 18:57
 */
public interface IShardingManager {
    void put(ReadQuery query, Serializable data);
    void delete(Query query);
    DataRetrieval getData(ReadQuery query);
}
