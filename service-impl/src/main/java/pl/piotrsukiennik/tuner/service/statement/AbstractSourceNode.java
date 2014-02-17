package pl.piotrsukiennik.tuner.service.statement;

import pl.piotrsukiennik.tuner.DataSource;
import pl.piotrsukiennik.tuner.SharderNode;
import pl.piotrsukiennik.tuner.dto.DataRetrieval;
import pl.piotrsukiennik.tuner.exception.DataRetrievalException;
import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.model.query.datasource.DataSourceIdentity;
import pl.piotrsukiennik.tuner.service.util.RowSet;

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
public abstract class AbstractSourceNode implements DataSource, SharderNode {

    private DataSourceIdentity dataSourceIdentity;

    private Set<String> supportedQueries = new LinkedHashSet<String>();


    protected AbstractSourceNode( DataSourceIdentity dataSourceIdentity ) {
        this.dataSourceIdentity = dataSourceIdentity;

    }

    public DataSourceIdentity getDataSourceIdentity() {
        return dataSourceIdentity;
    }

    public void setDataSourceIdentity( DataSourceIdentity persistedDataSourceIdentity ) {
        this.dataSourceIdentity = persistedDataSourceIdentity;
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
        if ( !( o instanceof AbstractSourceNode ) ) {
            return false;
        }
        AbstractSourceNode that = (AbstractSourceNode) o;
        if ( dataSourceIdentity != null ) {
            return dataSourceIdentity.equals( that.getDataSourceIdentity() );
        }
        else {
            return that.dataSourceIdentity != null;
        }
    }

    @Override
    public int hashCode() {
        int result = dataSourceIdentity != null ? dataSourceIdentity.hashCode() : 0;
        result = 31 * result + ( dataSourceIdentity != null ? dataSourceIdentity.hashCode() : 0 );
        return result;
    }
}
