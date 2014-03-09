package pl.piotrsukiennik.tuner.aop;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.piotrsukiennik.tuner.PreparedStatementBuilder;
import pl.piotrsukiennik.tuner.exception.PreparedStatementInterceptException;

import java.sql.PreparedStatement;


/**
 * Author: Piotr Sukiennik
 * Date: 09.07.13
 * Time: 00:47
 */
@Component("preparedStatementInterceptingAdvice")
public class PSInterceptingAdvice implements InterceptingAdvice<PreparedStatement> {

    @Autowired
    private PreparedStatementBuilder PreparedStatementBuilder;

    @Override
    public PreparedStatement invoke( final MethodInvocation methodInvocation ) throws PreparedStatementInterceptException {
        try {
            PreparedStatement preparedStatement = (PreparedStatement) methodInvocation.proceed();
            String queryString = (String) methodInvocation.getArguments()[0];
            return PreparedStatementBuilder.build( preparedStatement, queryString );
        }
        catch ( Throwable throwable ) {
            throw new PreparedStatementInterceptException( throwable );
        }
    }


}
