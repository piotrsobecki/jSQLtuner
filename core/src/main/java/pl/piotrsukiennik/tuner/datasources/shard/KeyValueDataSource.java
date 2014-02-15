

package pl.piotrsukiennik.tuner.datasources.shard;

import pl.piotrsukiennik.tuner.DataSourceMetaData;
import pl.piotrsukiennik.tuner.KeyValueService;
import pl.piotrsukiennik.tuner.datasources.AbstractDataSource;
import pl.piotrsukiennik.tuner.datasources.IdentifierMetaData;
import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.model.query.datasource.DataSourceIdentity;

import javax.sql.rowset.CachedRowSet;

/**
 * Author: Piotr Sukiennik
 * Date: 31.08.13
 * Time: 19:17
 */
public class KeyValueDataSource extends AbstractDataSource {
    private KeyValueService keyValueService;


    public static DataSourceIdentity getIdentity( String identifier ) {
        DataSourceMetaData dataSourceMetaData = new IdentifierMetaData( identifier );
        return new DataSourceIdentity( KeyValueDataSource.class, dataSourceMetaData.getIdentifier() );
    }

    public KeyValueDataSource( KeyValueService keyValueService ) {
        super( KeyValueDataSource.getIdentity( keyValueService.getIdentifier() ) );
        this.keyValueService = keyValueService;
    }

    @Override
    public CachedRowSet getData( ReadQuery query ) {
        String key = getKey( query.getHash() );
        return (CachedRowSet) keyValueService.get( key );
    }

    @Override
    public void putData( ReadQuery query, CachedRowSet data ) {
        String key = getKey( query.getHash() );
        keyValueService.put( key, data );
    }

    @Override
    public void deleteData( Query query ) {
        String key = getKey( query.getHash() );
        keyValueService.delete( key );
    }


    protected String getKey( String suffix ) {
        return suffix;
    }
}
