package pl.piotrsukiennik.tuner.service;

import pl.piotrsukiennik.tuner.DataSource;
import pl.piotrsukiennik.tuner.dto.ReadQueryExecutionResult;
import pl.piotrsukiennik.tuner.model.datasource.DataSourceIdentity;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;

import java.util.Collection;

/**
 * @author Piotr Sukiennik
 * @date 13.02.14
 */
public interface DataSourceSelector {

    DataSourceIdentity selectDataSource( ReadQuery readQuery );

    Collection<DataSourceIdentity> getSupportingDataSources( ReadQuery readQuery );

    void submitExecution( ReadQueryExecutionResult readQueryExecutionResult );

    void scheduleSelection( ReadQuery readQuery, DataSource dataSource );

    void removeSelectionOptions( ReadQuery query );
}
