package pl.piotrsukiennik.tuner.datasources;

import pl.piotrsukiennik.tuner.DataSharder;
import pl.piotrsukiennik.tuner.DataSource;
import pl.piotrsukiennik.tuner.DataSourceMetaData;
import pl.piotrsukiennik.tuner.dto.DataRetrieval;
import pl.piotrsukiennik.tuner.exception.DataRetrievalException;
import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.model.query.execution.DataSourceIdentity;
import pl.piotrsukiennik.tuner.utils.RowSet;

import javax.sql.rowset.CachedRowSet;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Author: Piotr Sukiennik
 * Date: 26.08.13
 * Time: 22:24
 */
public abstract class AbstractDataSource implements DataSource, DataSharder {

    private DataSourceMetaData dataSourceMetaData;

    private DataSourceIdentity dataSourceIdentity;

    private Set<String> supportedQueries = new LinkedHashSet<String>();


    protected AbstractDataSource( DataSourceMetaData dataSourceMetaData ) {
        this.dataSourceMetaData = dataSourceMetaData;
    }

    public DataSourceIdentity getDataSourceIdentity() {
        return dataSourceIdentity;
    }

    public void setDataSourceIdentity( DataSourceIdentity persistedDataSourceIdentity ) {
        this.dataSourceIdentity = persistedDataSourceIdentity;
    }

    @Override
    public DataSourceMetaData getMetaData() {
        return dataSourceMetaData;
    }

    @Override
    public final DataRetrieval get( ReadQuery query ) throws DataRetrievalException {
        DataRetrieval dataRetrieval = new DataRetrieval();
        long start = System.nanoTime();
        ResultSet resultSet = getData( query );
        if ( resultSet == null ) {
            throw new DataRetrievalException( "No data found for query" );
        }
        try {
            resultSet = RowSet.clone( resultSet );
        }
        catch ( SQLException e ) {
            throw new DataRetrievalException( e );
        }
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

    protected abstract ResultSet getData( ReadQuery query ) throws DataRetrievalException;

    protected abstract void deleteData( Query query );

    protected abstract void putData( ReadQuery query, CachedRowSet data );

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) {
            return true;
        }
        if ( !( o instanceof AbstractDataSource ) ) {
            return false;
        }
        AbstractDataSource that = (AbstractDataSource) o;
        if ( dataSourceMetaData != null ) {
            return dataSourceMetaData.getIdentifier().equals( that.getMetaData().getIdentifier() );
        }
        else {
            return that.dataSourceMetaData != null;
        }
    }

    @Override
    public int hashCode() {
        int result = dataSourceMetaData != null ? dataSourceMetaData.hashCode() : 0;
        result = 31 * result + ( dataSourceIdentity != null ? dataSourceIdentity.hashCode() : 0 );
        return result;
    }
}
