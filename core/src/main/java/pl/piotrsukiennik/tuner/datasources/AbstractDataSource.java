package pl.piotrsukiennik.tuner.datasources;

import pl.piotrsukiennik.tuner.persistance.model.query.Query;

import java.sql.ResultSet;

/**
 * Author: Piotr Sukiennik
 * Date: 26.08.13
 * Time: 22:24
 */
public abstract class AbstractDataSource implements IDataSource {

    private IDataSourceMetaData dataSourceMetaData;

    protected AbstractDataSource(IDataSourceMetaData dataSourceMetaData) {
        this.dataSourceMetaData = dataSourceMetaData;
    }

    @Override
    public IDataSourceMetaData getMetaData() {
        return dataSourceMetaData;
    }

    @Override
    public final ResultSet getData(Query query) throws Throwable {
        long start = System.currentTimeMillis();
        ResultSet resultSet = get(query);
        long end = System.currentTimeMillis();
        query.setExecutionTimeMillis(end-start);
        return resultSet;
    }

    protected abstract ResultSet get(Query query) throws Throwable;
}
