package pl.piotrsukiennik.tuner.model;

import pl.piotrsukiennik.tuner.exception.DataRetrievalException;
import pl.piotrsukiennik.tuner.model.datasource.DataSourceIdentity;
import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;

import java.sql.ResultSet;

/**
 * Author: Piotr Sukiennik
 * Date: 26.08.13
 * Time: 22:00
 */
public interface DataSource {
    DataSourceIdentity getDataSourceIdentity();
    ResultSet execute( ReadQuery query ) throws DataRetrievalException;
    <QER extends ReadQueryExecutionResult>  void distribute( QER data );
    void delete( Query query );
}
