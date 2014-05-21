package pl.piotrsukiennik.tuner;

import pl.piotrsukiennik.tuner.dto.DataRetrieval;
import pl.piotrsukiennik.tuner.exception.DataRetrievalException;
import pl.piotrsukiennik.tuner.model.datasource.DataSourceIdentity;
import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;

import javax.sql.rowset.CachedRowSet;
import java.sql.ResultSet;

/**
 * Author: Piotr Sukiennik
 * Date: 26.08.13
 * Time: 22:00
 */
public interface DataSource {

    DataSourceIdentity getDataSourceIdentity();

    ResultSet get( ReadQuery query ) throws DataRetrievalException;

    void put( ReadQuery query, DataRetrieval dataRetrieval );
    void put( ReadQuery query, CachedRowSet data );

    void delete( Query query );

    boolean contains( ReadQuery query );
}
