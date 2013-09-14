package pl.piotrsukiennik.tuner.datasources.shard;

import pl.piotrsukiennik.tuner.datasources.DataRetrieval;
import pl.piotrsukiennik.tuner.datasources.IDataSource;
import pl.piotrsukiennik.tuner.persistance.model.query.Query;
import pl.piotrsukiennik.tuner.persistance.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.persistance.model.query.SelectQuery;

import javax.sql.rowset.CachedRowSet;
import java.io.Serializable;

/**
 * Author: Piotr Sukiennik
 * Date: 31.08.13
 * Time: 18:57
 */
public interface IDataSharder extends IDataSource{
    DataRetrieval get(ReadQuery query) throws Throwable;
    boolean contains(ReadQuery query);
    void put(ReadQuery query, Serializable data);
    void delete(Query query);
}
