package pl.piotrsukiennik.tuner.datasources;

import pl.piotrsukiennik.tuner.IDataSharder;
import pl.piotrsukiennik.tuner.IDataSource;
import pl.piotrsukiennik.tuner.IDataSourceMetaData;
import pl.piotrsukiennik.tuner.dto.DataRetrieval;
import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.model.query.execution.DataSource;
import pl.piotrsukiennik.tuner.utils.RowSet;

import javax.sql.rowset.CachedRowSet;
import java.sql.ResultSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Author: Piotr Sukiennik
 * Date: 26.08.13
 * Time: 22:24
 */
public abstract class AbstractDataSource implements IDataSource, IDataSharder {

    private IDataSourceMetaData dataSourceMetaData;

    private DataSource persistedDataSource;

    private Set<String> supportedQueries = new LinkedHashSet<String>();


    protected AbstractDataSource( IDataSourceMetaData dataSourceMetaData ) {
        this.dataSourceMetaData = dataSourceMetaData;
    }

    public DataSource getPersistedDataSource() {
        return persistedDataSource;
    }

    public void setPersistedDataSource( DataSource persistedDataSource ) {
        this.persistedDataSource = persistedDataSource;
    }

    @Override
    public IDataSourceMetaData getMetaData() {
        return dataSourceMetaData;
    }

    @Override
    public final DataRetrieval get( ReadQuery query ) throws Throwable {
        DataRetrieval dataRetrieval = new DataRetrieval();
        long start = System.nanoTime();
        ResultSet resultSet = getData( query );
        resultSet = RowSet.clone( resultSet );
        long end = System.nanoTime();
        dataRetrieval.setExecutionTimeNano( end - start );
        dataRetrieval.setResultSet( resultSet );
        return dataRetrieval;
    }

    @Override
    public void put( ReadQuery query, CachedRowSet data ) {
        putData( query, data );
        supportedQueries.add( query.getHash() );

    }


    @Override
    public boolean contains( ReadQuery query ) {
        return supportedQueries.contains( query.getHash() );
    }

    @Override
    public void delete( Query query ) {

        deleteData( query );
    }

    protected abstract ResultSet getData( ReadQuery query ) throws Throwable;

    protected abstract void deleteData( Query query );

    protected abstract void putData( ReadQuery query, CachedRowSet data );

    @Override
    public boolean equals( Object o ) {
        if ( this == o )
            return true;
        if ( !( o instanceof AbstractDataSource ) )
            return false;
        AbstractDataSource that = (AbstractDataSource) o;
        if ( dataSourceMetaData != null ? !dataSourceMetaData.getIdentifier().equals( that.dataSourceMetaData.getIdentifier() ) :
         that.dataSourceMetaData != null )
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = dataSourceMetaData != null ? dataSourceMetaData.hashCode() : 0;
        result = 31 * result + ( persistedDataSource != null ? persistedDataSource.hashCode() : 0 );
        return result;
    }
}
