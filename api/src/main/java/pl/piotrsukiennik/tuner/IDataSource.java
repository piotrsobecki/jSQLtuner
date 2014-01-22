package pl.piotrsukiennik.tuner;

import pl.piotrsukiennik.tuner.dto.DataRetrieval;
import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.model.query.execution.DataSource;

import javax.sql.rowset.CachedRowSet;

/**
 * Author: Piotr Sukiennik
 * Date: 26.08.13
 * Time: 22:00
 */
public interface IDataSource {
    void setPersistedDataSource( DataSource ds );

    DataSource getPersistedDataSource();

    IDataSourceMetaData getMetaData();

    DataRetrieval get( ReadQuery query ) throws Throwable;

    void put( ReadQuery query, CachedRowSet resultSet );

    void delete( Query query );

    boolean contains( ReadQuery query );
}
