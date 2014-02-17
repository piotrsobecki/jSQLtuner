package pl.piotrsukiennik.tuner.service.ai.impl;

import pl.piotrsukiennik.ai.id.GenericIdentifier;
import pl.piotrsukiennik.tuner.model.query.datasource.DataSourceIdentity;

/**
 * @author Piotr Sukiennik
 * @date 13.02.14
 */
public class DataSourceIdentifier extends GenericIdentifier<String> {
    public DataSourceIdentifier( DataSourceIdentity dataSource ) {
        super( dataSource.getIdentifier() );
    }

    DataSourceIdentifier( String identifier ) {
        super( identifier );
    }
}
