package pl.piotrsukiennik.tuner.datasource;

import pl.piotrsukiennik.tuner.DataSource;
import pl.piotrsukiennik.tuner.dto.ReadQueryExecutionResult;
import pl.piotrsukiennik.tuner.model.datasource.DataSourceIdentity;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;

import javax.sql.rowset.CachedRowSet;

/**
 * Author: Piotr Sukiennik
 * Date: 26.08.13
 * Time: 22:24
 */
public abstract class AbstractDataSource implements DataSource {

    private DataSourceIdentity dataSourceIdentity;

    protected AbstractDataSource( DataSourceIdentity dataSourceIdentity ) {
        this.dataSourceIdentity = dataSourceIdentity;

    }

    public DataSourceIdentity getDataSourceIdentity() {
        return dataSourceIdentity;
    }


    @Override
    public void distribute( ReadQueryExecutionResult data ) {
        ReadQuery query =data.getReadQuery();
        put( query, data.getResultSet() );
    }

    abstract protected void put( ReadQuery query, CachedRowSet data );

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
