package pl.piotrsukiennik.tuner.statement.advisor;

import org.aopalliance.intercept.MethodInvocation;
import pl.piotrsukiennik.tuner.datasources.DataSourcesManager;
import pl.piotrsukiennik.tuner.datasources.StatementMethodInvocationDataSource;
import pl.piotrsukiennik.tuner.exception.DataRetrievalException;
import pl.piotrsukiennik.tuner.exception.PreparedStatementInterceptException;
import pl.piotrsukiennik.tuner.model.query.SelectQuery;
import pl.piotrsukiennik.tuner.persistance.DaoHolder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Author: Piotr Sukiennik
 * Date: 28.07.13
 * Time: 15:58
 */
public class ReadQueryAdvice extends QueryAdvice<SelectQuery, ResultSet> {

    private DataSourcesManager manager;


    public ReadQueryAdvice( DataSourcesManager dataSourcesManager, SelectQuery query ) {
        super( query );
        this.manager = dataSourcesManager;
    }

    @Override
    public ResultSet invoke( final MethodInvocation methodInvocation ) throws PreparedStatementInterceptException {
        try {
            manager.setDataForQuery( query, new StatementMethodInvocationDataSource( (Statement) methodInvocation.getThis(), methodInvocation, query ) );
            return manager.getData( query );
        }
        catch ( SQLException | DataRetrievalException e ) {
            DaoHolder.getLogDao().logException( query.getValue(), e );
            throw new PreparedStatementInterceptException( e );
        }
    }


    public DataSourcesManager getManager() {
        return manager;
    }

    public void setManager( DataSourcesManager manager ) {
        this.manager = manager;
    }
}
