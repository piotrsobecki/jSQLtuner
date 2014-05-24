package pl.piotrsukiennik.tuner.model;

import pl.piotrsukiennik.ai.id.GenericIdentifier;
import pl.piotrsukiennik.tuner.model.datasource.DataSourceIdentity;

/**
 * @author Piotr Sukiennik
 * @date 13.02.14
 */
public class DataSourceIdentifier extends GenericIdentifier<String> {
    private DataSourceIdentity dataSourceIdentity;

    public DataSourceIdentifier( DataSourceIdentity dataSourceIdentity ) {
        super( dataSourceIdentity.getIdentifier() );
        this.dataSourceIdentity=dataSourceIdentity;
    }
    public DataSourceIdentifier( DataSource dataSource ) {
        this( dataSource.getDataSourceIdentity() );
    }

    public DataSourceIdentity getDataSourceIdentity() {
        return dataSourceIdentity;
    }
}
