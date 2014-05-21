package pl.piotrsukiennik.tuner.datasource;

import pl.piotrsukiennik.tuner.exception.DataRetrievalException;
import pl.piotrsukiennik.tuner.exception.WriteToStatementException;
import pl.piotrsukiennik.tuner.exception.WrongStatementException;
import pl.piotrsukiennik.tuner.model.datasource.DataSourceIdentity;
import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.util.Objects2;

import javax.sql.rowset.CachedRowSet;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

/**
 * Author: Piotr Sukiennik
 * Date: 26.08.13
 * Time: 23:01
 */
public abstract class InterceptorDataSource extends AbstractDataSource {

    private static final String GET_DATA_EXCEPTION_FORMAT = InterceptorDataSource.class.getSimpleName() + " does not support query (%s).";

    private ReadQuery query;

    public InterceptorDataSource( Statement statement,
                                  ReadQuery query ) throws SQLException {
        super( new DataSourceIdentity( InterceptorDataSource.class, statement.getConnection().getMetaData().getURL() ) );
        this.query = query;
    }

    @Override
    public ResultSet get( ReadQuery query ) throws DataRetrievalException {
        try {
            if ( Objects2.eq( this.query, query ) ) {
                return proceed();
            }
            throw new WrongStatementException( query, getDataSourceIdentity() );
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
        throw new WriteToStatementException();
    }

    @Override
    public void delete( Query query ) {
        throw new WriteToStatementException();
    }

    @Override
    public void delete( Query query, Collection<ReadQuery> queriesToInvalidate ) {
        throw new WriteToStatementException();
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
