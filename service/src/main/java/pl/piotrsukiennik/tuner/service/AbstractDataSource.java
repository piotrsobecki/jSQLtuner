package pl.piotrsukiennik.tuner.service;

import pl.piotrsukiennik.tuner.DataSource;
import pl.piotrsukiennik.tuner.ShardNode;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.model.query.datasource.DataSourceIdentity;

import javax.sql.rowset.CachedRowSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Author: Piotr Sukiennik
 * Date: 26.08.13
 * Time: 22:24
 */
public abstract class AbstractDataSource implements DataSource, ShardNode {

    private DataSourceIdentity dataSourceIdentity;

    private Set<String> supportedQueries = new LinkedHashSet<String>();

    protected AbstractDataSource( DataSourceIdentity dataSourceIdentity ) {
        this.dataSourceIdentity = dataSourceIdentity;

    }

    public DataSourceIdentity getDataSourceIdentity() {
        return dataSourceIdentity;
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
