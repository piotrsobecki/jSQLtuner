

package pl.piotrsukiennik.tuner.datasources.shard;

import pl.piotrsukiennik.tuner.KeyValueDataSource;
import pl.piotrsukiennik.tuner.KeyValueService;
import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.model.query.datasource.DataSourceIdentity;
import pl.piotrsukiennik.tuner.service.statement.AbstractDataSource;

import javax.sql.rowset.CachedRowSet;

/**
 * Author: Piotr Sukiennik
 * Date: 31.08.13
 * Time: 19:17
 */
public class KeyValueDataSourceImpl extends AbstractDataSource implements KeyValueDataSource {

    private KeyValueService keyValueService;

    public KeyValueDataSourceImpl( KeyValueService keyValueService ) {
        super( new DataSourceIdentity( KeyValueDataSource.class, keyValueService.getIdentifier() ) );
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
