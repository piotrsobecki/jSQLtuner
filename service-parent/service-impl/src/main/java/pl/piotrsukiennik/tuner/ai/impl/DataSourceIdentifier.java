package pl.piotrsukiennik.tuner.ai.impl;

import pl.piotrsukiennik.ai.id.GenericIdentifier;
import pl.piotrsukiennik.tuner.DataSource;
import pl.piotrsukiennik.tuner.model.datasource.DataSourceIdentity;

/**
 * @author Piotr Sukiennik
 * @date 13.02.14
 */
public class DataSourceIdentifier extends GenericIdentifier<String> {
    public DataSourceIdentifier( DataSourceIdentity dataSource ) {
        super( dataSource.getIdentifier() );
    }
    public DataSourceIdentifier( DataSource dataSource ) {
        this( dataSource.getDataSourceIdentity() );
    }

}
