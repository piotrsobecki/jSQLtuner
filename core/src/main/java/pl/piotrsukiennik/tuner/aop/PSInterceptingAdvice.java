package pl.piotrsukiennik.tuner.aop;

import org.aopalliance.intercept.MethodInvocation;
import pl.piotrsukiennik.tuner.statement.PreparedStatementBuilder;
import pl.piotrsukiennik.tuner.exception.PreparedStatementInterceptException;

import java.sql.PreparedStatement;


/**
 * Author: Piotr Sukiennik
 * Date: 09.07.13
 * Time: 00:47
 */
public class PSInterceptingAdvice implements InterceptingAdvice<PreparedStatement> {
    private PreparedStatementBuilder preparedStatementBuilder;

    public PSInterceptingAdvice( PreparedStatementBuilder preparedStatementBuilder ) {
        this.preparedStatementBuilder = preparedStatementBuilder;
    }

    @Override
    public PreparedStatement invoke( final MethodInvocation methodInvocation ) throws PreparedStatementInterceptException {
        try {
            PreparedStatement preparedStatement = (PreparedStatement) methodInvocation.proceed();
            String queryString = (String) methodInvocation.getArguments()[0];
            return preparedStatementBuilder.build( preparedStatement, queryString );
        }
        catch ( Throwable throwable ) {
            throw new PreparedStatementInterceptException( throwable );
        }
    }


}
