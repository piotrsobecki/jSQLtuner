package pl.piotrsukiennik.tuner.service;

import pl.piotrsukiennik.tuner.KeyValueService;
import pl.piotrsukiennik.tuner.datasource.AbstractDataSource;
import pl.piotrsukiennik.tuner.model.datasource.DataSourceIdentity;
import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;

import javax.sql.rowset.CachedRowSet;

/**
 * Author: Piotr Sukiennik
 * Date: 31.08.13
 * Time: 19:17
 */
public class KeyValueServiceAdapter extends AbstractDataSource {

    private KeyValueService keyValueService;

    public KeyValueServiceAdapter( KeyValueService keyValueService ) {
        super( new DataSourceIdentity( KeyValueServiceAdapter.class, keyValueService.getIdentifier() ) );
        this.keyValueService = keyValueService;
    }

    @Override
    public CachedRowSet execute( ReadQuery query ) {
        String key = getKey( query.getHash() );
        return (CachedRowSet) keyValueService.get( key );
    }

    @Override
    public void put( ReadQuery query, CachedRowSet data ) {
        String key = getKey( query.getHash() );
        keyValueService.put( key, data );
    }

    @Override
    public void delete( Query query ) {
        String key = getKey( query.getHash() );
        keyValueService.delete( key );
    }

    protected String getKey( String suffix ) {
        return suffix;
    }
}
