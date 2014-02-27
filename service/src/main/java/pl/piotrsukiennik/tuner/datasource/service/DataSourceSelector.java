package pl.piotrsukiennik.tuner.datasource.service;

import pl.piotrsukiennik.tuner.dto.DataRetrieval;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.model.query.datasource.DataSourceIdentity;

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
