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
public interface DataSourceSelectionService {

    DataSourceIdentity selectDataSource( ReadQuery readQuery );

    Collection<DataSourceIdentity> getSupportingDataSources( ReadQuery readQuery );

    public Collection<DataSourceIdentity>  getNewSupportingDataSources(ReadQueryExecutionResult data);

    void  submitExecution( ReadQueryExecutionResult readQueryExecutionResult );

    void scheduleSelection( ReadQuery readQuery, DataSourceIdentity dataSource );

    void removeSelectionOptions( ReadQuery query );
}
