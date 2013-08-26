package pl.piotrsukiennik.tuner.datasources;

import pl.piotrsukiennik.tuner.persistance.model.query.Query;
import pl.piotrsukiennik.tuner.persistance.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.util.RowSet;

import javax.sql.rowset.CachedRowSet;
import java.sql.ResultSet;
import java.util.List;

/**
 * Author: Piotr Sukiennik
 * Date: 26.08.13
 * Time: 22:44
 */
public class CachingDataSourceWrapper implements IDataSource {
    private IDataSource source;
    private List<IDataSource> shard;

    public CachingDataSourceWrapper(IDataSource source, List<IDataSource> shard) {
        this.source = source;
        this.shard=shard;
    }

    @Override
    public IDataSourceMetaData getMetaData() {
        return source.getMetaData();
    }

    @Override
    public ResultSet getData(Query query) throws Throwable {
        ResultSet resultSet = source.getData(query);
        CachedRowSet cachedResultSet = RowSet.cached(resultSet);
        if (query instanceof ReadQuery){
            ((ReadQuery) query).setRowsReturned(cachedResultSet.size());
        }
        for (IDataSource dataSource:shard){
            dataSource.putData(query,cachedResultSet);
        }
        return cachedResultSet;
    }

    @Override
    public void putData(Query query, ResultSet resultSet) {
        source.putData(query,resultSet);
    }
}
