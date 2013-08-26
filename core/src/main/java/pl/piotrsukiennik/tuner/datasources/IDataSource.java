package pl.piotrsukiennik.tuner.datasources;

import pl.piotrsukiennik.tuner.persistance.model.query.Query;

import java.sql.ResultSet;

/**
 * Author: Piotr Sukiennik
 * Date: 26.08.13
 * Time: 22:00
 */
public interface IDataSource {
    IDataSourceMetaData getMetaData();
    ResultSet getData(Query query) throws Throwable;
    void putData(Query query,ResultSet resultSet);
}
