package pl.piotrsukiennik.tuner.datasources;

import pl.piotrsukiennik.tuner.persistance.model.query.ReadQuery;
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
    public final DataRetrieval getData(ReadQuery query) throws Throwable {
        DataRetrieval dataRetrieval = new DataRetrieval();
        long start = System.nanoTime();
        ResultSet resultSet = get(query);
        long end = System.nanoTime();
        dataRetrieval.setExecutionTimeNano(end - start);
        dataRetrieval.setResultSet(resultSet);
        return dataRetrieval;
    }

    protected abstract ResultSet get(ReadQuery query) throws Throwable;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractDataSource)) return false;
        AbstractDataSource that = (AbstractDataSource) o;
        if (dataSourceMetaData != null ? !dataSourceMetaData.getIdentifier().equals(that.dataSourceMetaData.getIdentifier()) :
                that.dataSourceMetaData != null)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = dataSourceMetaData != null ? dataSourceMetaData.hashCode() : 0;
        result = 31 * result + (persistedDataSource != null ? persistedDataSource.hashCode() : 0);
        return result;
    }
}
