package pl.piotrsukiennik.tuner.service;

import pl.piotrsukiennik.tuner.dto.DataRetrieval;
import pl.piotrsukiennik.tuner.model.datasource.DataSourceIdentity;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;

/**
 * @author Piotr Sukiennik
 * @date 13.02.14
 */
public interface DataSourceSelector {
    DataSourceIdentity getDataSource( ReadQuery readQuery );

    void submitDataRetrieval( DataRetrieval dataRetrieval );

    public void scheduleDataRetrieval( ReadQuery readQuery, DataSourceIdentity dataSource );

    void removeDataSources( ReadQuery query );
}
