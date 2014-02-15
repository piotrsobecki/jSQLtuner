package pl.piotrsukiennik.tuner.datasources;

import org.aopalliance.intercept.MethodInvocation;
import pl.piotrsukiennik.tuner.DataSourceMetaData;
import pl.piotrsukiennik.tuner.exception.DataRetrievalException;
import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.model.query.datasource.DataSourceIdentity;

import javax.sql.rowset.CachedRowSet;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Author: Piotr Sukiennik
 * Date: 26.08.13
 * Time: 23:01
 */
public class StatementMethodInvocationDataSource extends AbstractDataSource {


    private MethodInvocation methodInvocation;

    private ReadQuery readQuery;

    public static DataSourceIdentity getIdentity( Connection connection ) {
        DataSourceMetaData dataSourceMetaData = new ConnectionDataSourceMetaData( connection );
        return new DataSourceIdentity( StatementMethodInvocationDataSource.class, dataSourceMetaData.getIdentifier() );
    }

    public StatementMethodInvocationDataSource( Statement statement,
                                                MethodInvocation methodInvocation,
                                                ReadQuery readQuery ) throws SQLException {
        super( StatementMethodInvocationDataSource.getIdentity( statement.getConnection() ) );
        this.methodInvocation = methodInvocation;
        this.readQuery = readQuery;
    }

    @Override
    protected ResultSet getData( ReadQuery query ) throws DataRetrievalException {
        try {
            return (ResultSet) methodInvocation.proceed();
        }
        catch ( Throwable t ) {
            throw new DataRetrievalException( t );
        }
    }


    @Override
    public void putData( ReadQuery query, CachedRowSet resultSet ) {
        /*
         * You wouldn't put data on a query, would you?
         */
    }

    public ReadQuery getReadQuery() {
        return readQuery;
    }

    @Override
    protected void deleteData( Query query ) {
        /*
         * You wouldn't delete data from a query, would you?
         */
    }

    @Override
    public boolean equals( Object o ) {
        if ( o == this )
            return true;
        if ( o instanceof StatementMethodInvocationDataSource ) {
            ReadQuery oQ = ( (StatementMethodInvocationDataSource) o ).getReadQuery();
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
