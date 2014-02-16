package pl.piotrsukiennik.tuner.service.statement;

import pl.piotrsukiennik.tuner.exception.DataRetrievalException;
import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.model.query.datasource.DataSourceIdentity;

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

    private ReadQuery readQuery;

    public InterceptorDataSource( Statement statement,
                                  ReadQuery readQuery ) throws SQLException {
        super( new DataSourceIdentity( InterceptorDataSource.class, statement.getConnection().getMetaData().getURL() ) );
        this.readQuery = readQuery;
    }

    @Override
    protected ResultSet getData( ReadQuery query ) throws DataRetrievalException {
        try {
            return proceed();
        }
        catch ( SQLException e ) {
            throw new DataRetrievalException( e );
        }
    }

    protected abstract ResultSet proceed() throws SQLException;

    public ReadQuery getReadQuery() {
        return readQuery;
    }

    @Override
    public void putData( ReadQuery query, CachedRowSet resultSet ) {
        throw new WriteToStatementException();
    }

    @Override
    protected void deleteData( Query query ) {
        throw new WriteToStatementException();
    }

    @Override
    public boolean equals( Object o ) {
        if ( o == this )
            return true;
        if ( o instanceof InterceptorDataSource ) {
            ReadQuery oQ = ( (InterceptorDataSource) o ).getReadQuery();
            return ( ( getReadQuery().getId() == oQ.getId() ) && oQ.getId() != 0 )
             || getReadQuery().getHash().equals( oQ.getHash() );
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        return 31 * result + readQuery.hashCode();
    }
}
