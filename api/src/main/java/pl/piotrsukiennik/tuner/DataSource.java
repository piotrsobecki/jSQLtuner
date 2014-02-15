package pl.piotrsukiennik.tuner;

import pl.piotrsukiennik.tuner.dto.DataRetrieval;
import pl.piotrsukiennik.tuner.exception.DataRetrievalException;
import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.model.query.datasource.DataSourceIdentity;

import javax.sql.rowset.CachedRowSet;

/**
 * Author: Piotr Sukiennik
 * Date: 26.08.13
 * Time: 22:00
 */
public interface DataSource {

    void setDataSourceIdentity( DataSourceIdentity ds );

    DataSourceIdentity getDataSourceIdentity();

    DataRetrieval get( ReadQuery query ) throws DataRetrievalException;

    void put( ReadQuery query, CachedRowSet resultSet );

    void delete( Query query );

    boolean contains( ReadQuery query );
}
