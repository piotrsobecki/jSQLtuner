package pl.piotrsukiennik.tuner.statement.advisor;

import org.aopalliance.intercept.MethodInvocation;
import pl.piotrsukiennik.tuner.Sharder;
import pl.piotrsukiennik.tuner.exception.PreparedStatementInterceptException;
import pl.piotrsukiennik.tuner.model.query.WriteQuery;
import pl.piotrsukiennik.tuner.model.query.WriteQueryExecution;
import pl.piotrsukiennik.tuner.persistance.DaoHolder;

import java.sql.Timestamp;

/**
 * Author: Piotr Sukiennik
 * Date: 28.07.13
 * Time: 15:58
 */
public class WriteQueryAdvice extends QueryAdvice<WriteQuery, Object> {
    private Sharder sharder;

    public WriteQueryAdvice( Sharder sharder, WriteQuery query ) {
        super( query );
        this.sharder = sharder;
    }

    @Override
    public Object invoke( MethodInvocation methodInvocation ) throws PreparedStatementInterceptException {
        try {
            Object ret = methodInvocation.proceed();
            Integer rowsAffected = (Integer) ret;
            WriteQueryExecution writeQueryExecution = new WriteQueryExecution();
            writeQueryExecution.setRowsAffected( rowsAffected );
            writeQueryExecution.setTimestamp( new Timestamp( System.currentTimeMillis() ) );
            writeQueryExecution.setQuery( query );
            DaoHolder.getCommonDao().create( writeQueryExecution );
            if ( rowsAffected > 0 ) {
                sharder.delete( query );
            }
            return ret;
        }
        catch ( Throwable e ) {
            DaoHolder.getLogDao().logException( query.getValue(), e );
            throw new PreparedStatementInterceptException( e );
        }
    }
}
