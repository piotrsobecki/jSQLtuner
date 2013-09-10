package pl.piotrsukiennik.tuner.datasources;

import pl.piotrsukiennik.tuner.persistance.model.query.Query;
import pl.piotrsukiennik.tuner.persistance.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.persistance.model.query.SelectQuery;
import pl.piotrsukiennik.tuner.persistance.model.query.execution.DataSource;

import java.io.Serializable;
import java.sql.ResultSet;

/**
 * Author: Piotr Sukiennik
 * Date: 26.08.13
 * Time: 22:00
 */
public interface IDataSource {
    void  setPersistedDataSource(DataSource ds);
    DataSource getPersistedDataSource();
    IDataSourceMetaData getMetaData();
    DataRetrieval getData(ReadQuery query) throws Throwable;
    void putData(ReadQuery query,Serializable resultSet);
}
