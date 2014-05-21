package pl.piotrsukiennik.tuner.service;

import pl.piotrsukiennik.tuner.DataSource;
import pl.piotrsukiennik.tuner.dto.DataRetrieval;
import pl.piotrsukiennik.tuner.model.datasource.DataSourceIdentity;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;

import java.util.Collection;

/**
 * @author Piotr Sukiennik
 * @date 13.02.14
 */
public interface DataSourceSelector {

    DataSourceIdentity select( ReadQuery readQuery );

    <DS extends DataSource> Collection<DS> possible( Collection<DS> dataSources, ReadQuery readQuery, DataRetrieval dataRetrieval );

    Collection<DataSourceIdentity> supporting( ReadQuery readQuery );

    void submit( DataRetrieval dataRetrieval );

    void schedule( ReadQuery readQuery, DataSource dataSource );

    void removeOptions( ReadQuery query );
}
