package pl.piotrsukiennik.tuner.service.ai;

import pl.piotrsukiennik.tuner.dto.DataRetrieval;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.model.query.datasource.DataSourceIdentity;

/**
 * @author Piotr Sukiennik
 * @date 13.02.14
 */
public interface DataSourceSelector {
    DataSourceIdentity selectDataSourceForQuery( ReadQuery readQuery );

    void submitDataRetrieval( DataSourceIdentity dataSource, ReadQuery readQuery, DataRetrieval dataRetrieval );

    public void scheduleDataRetrieval( DataSourceIdentity dataSource, ReadQuery readQuery );

    void removeDataSourcesForQuery( ReadQuery query );
}
