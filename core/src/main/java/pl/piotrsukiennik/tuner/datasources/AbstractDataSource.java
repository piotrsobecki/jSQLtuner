package pl.piotrsukiennik.tuner.datasources;

import pl.piotrsukiennik.tuner.persistance.model.query.Query;
import pl.piotrsukiennik.tuner.persistance.model.query.SelectQuery;
import pl.piotrsukiennik.tuner.persistance.model.query.execution.DataSource;

import java.sql.ResultSet;

/**
 * Author: Piotr Sukiennik
 * Date: 26.08.13
 * Time: 22:24
 */
public abstract class AbstractDataSource implements IDataSource {

    private IDataSourceMetaData dataSourceMetaData;
    private DataSource persistedDataSource;
    protected AbstractDataSource(IDataSourceMetaData dataSourceMetaData) {
        this.dataSourceMetaData = dataSourceMetaData;
    }
    public DataSource getPersistedDataSource() {
        return persistedDataSource;
    }

    public void setPersistedDataSource(DataSource persistedDataSource) {
        this.persistedDataSource = persistedDataSource;
    }
    @Override
    public IDataSourceMetaData getMetaData() {
        return dataSourceMetaData;
    }

    @Override
    public final DataRetrieval getData(SelectQuery query) throws Throwable {
        DataRetrieval dataRetrieval = new DataRetrieval();
        long start = System.currentTimeMillis();
        ResultSet resultSet = get(query);
        long end = System.currentTimeMillis();
        dataRetrieval.setExecutionTimeMillis(end-start);
        dataRetrieval.setResultSet(resultSet);
        return dataRetrieval;
    }

    protected abstract ResultSet get(SelectQuery query) throws Throwable;
}
