

package pl.piotrsukiennik.tuner.datasources.shard;

import org.springframework.stereotype.Component;
import pl.piotrsukiennik.tuner.datasources.AbstractDataSource;
import pl.piotrsukiennik.tuner.datasources.IDataSourceMetaData;
import pl.piotrsukiennik.tuner.datasources.IdentifierMetaData;
import pl.piotrsukiennik.tuner.datasources.keyvalue.IKeyValueService;
import pl.piotrsukiennik.tuner.persistance.model.query.Query;
import pl.piotrsukiennik.tuner.persistance.model.query.SelectQuery;

import javax.annotation.Resource;
import javax.sql.rowset.CachedRowSet;
import java.io.Serializable;
import java.sql.ResultSet;
import java.util.concurrent.TimeUnit;

/**
 * Author: Piotr Sukiennik
 * Date: 31.08.13
 * Time: 19:17
 */
public class KeyValueDataSharder extends AbstractDataSource implements IDataSharder{
    private
    IKeyValueService keyValueService;

    public KeyValueDataSharder(IKeyValueService keyValueService) {
        super(new IdentifierMetaData(keyValueService.getIdentifier()));
        this.keyValueService = keyValueService;
    }

    @Override
    public CachedRowSet get(SelectQuery query) {
        String key =  getKey(query.getHash());
        return (CachedRowSet) keyValueService.get(key);
    }

    @Override
    public boolean contains(SelectQuery query) {
        String key =  getKey(query.getHash());
        return keyValueService.get(key)!=null;
    }

    @Override
    public void putData(SelectQuery query, Serializable data) {
        String key =  getKey(query.getHash());
        keyValueService.put(key, data);
    }

    @Override
    public void delete(Query query) {
        String key =  getKey(query.getHash());
        keyValueService.delete(key);
    }


    protected String getKey(String suffix){
        return keyValueService +suffix;
    }
}
