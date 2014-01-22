package pl.piotrsukiennik.tuner.statement.advisor;

import org.aopalliance.intercept.MethodInvocation;
import pl.piotrsukiennik.tuner.datasources.DataSourcesManager;
import pl.piotrsukiennik.tuner.exception.PreparedStatementInterceptException;
import pl.piotrsukiennik.tuner.model.query.WriteQuery;
import pl.piotrsukiennik.tuner.model.query.WriteQueryExecution;
import pl.piotrsukiennik.tuner.persistance.DaoHolder;

import java.sql.Timestamp;
import java.util.LinkedHashSet;

/**
 * Author: Piotr Sukiennik
 * Date: 28.07.13
 * Time: 15:58
 */
public class WriteQueryAdvice extends QueryAdvice<WriteQuery, Object> {
    private DataSourcesManager manager;

    public WriteQueryAdvice( DataSourcesManager dataSourcesManager, WriteQuery query ) {
        super( query );
        this.manager = dataSourcesManager;
    }

    @Override
    public Object invoke( MethodInvocation methodInvocation ) throws PreparedStatementInterceptException{
        try {
            Object ret = methodInvocation.proceed();
            Integer rowsAffected = (Integer) ret;
            WriteQueryExecution writeQueryExecution = new WriteQueryExecution();
            writeQueryExecution.setRowsAffected( rowsAffected );
            writeQueryExecution.setTimestamp( new Timestamp( System.currentTimeMillis() ) );
            if ( query.getWriteQueryExecutions() == null ) {
                query.setWriteQueryExecutions( new LinkedHashSet<WriteQueryExecution>() );
            }
            query.getWriteQueryExecutions().add( writeQueryExecution );
            if ( rowsAffected > 0 ) {
                manager.invalidate( query );
            }
            return ret;
        }
        catch ( Throwable e ) {
            DaoHolder.getLogDao().logException( query.getValue(), e );
            throw new PreparedStatementInterceptException( e );
        }
    }
}
