package pl.piotrsukiennik.tuner.model;

import pl.piotrsukiennik.tuner.exception.DataRetrievalException;
import pl.piotrsukiennik.tuner.exception.DataWriteException;
import pl.piotrsukiennik.tuner.model.datasource.DataSourceIdentity;
import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;

import javax.sql.rowset.CachedRowSet;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Author: Piotr Sukiennik
 * Date: 26.08.13
 * Time: 23:01
 */
public abstract class InterceptorDataSource extends AbstractDataSource {

    private ReadQuery query;

    public InterceptorDataSource( Statement statement,
                                  ReadQuery query ) throws SQLException {
        super( new DataSourceIdentity( InterceptorDataSource.class, statement.getConnection().getMetaData().getURL(),true ) );
        this.query = query;
    }

    @Override
    public ResultSet execute( ReadQuery query ) throws DataRetrievalException {
        try {
            if ( this.query.equals( query ) ) {
                return proceed();
            }
            throw new DataRetrievalException("This data source does not contain query",query, getDataSourceIdentity() );
        }
        catch ( SQLException e ) {
            throw new DataRetrievalException( e, query, getDataSourceIdentity() );
        }
    }

    protected abstract ResultSet proceed() throws SQLException;

    public ReadQuery getQuery() {
        return query;
    }

    @Override
    public void put( ReadQuery query, CachedRowSet resultSet ) {
        throw new DataWriteException("You cannot write to query source",query,getDataSourceIdentity());
    }

    @Override
    public void delete( Query query ) {
        throw new DataWriteException("You cannot delete from query source",query,getDataSourceIdentity());
    }

    @Override
    public boolean equals( Object o ) {
        if ( o == this )
            return true;
        if ( o instanceof InterceptorDataSource ) {
            ReadQuery oQ = ( (InterceptorDataSource) o ).getQuery();
            return ( ( getQuery().getId() == oQ.getId() ) && oQ.getId() != 0 )
             || getQuery().getHash().equals( oQ.getHash() );
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        return 31 * result + query.hashCode();
    }
}
