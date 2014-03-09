package pl.piotrsukiennik.tuner.aop;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.piotrsukiennik.tuner.StatementBuilder;
import pl.piotrsukiennik.tuner.exception.PreparedStatementInterceptException;

import java.sql.Statement;


/**
 * Author: Piotr Sukiennik
 * Date: 09.07.13
 * Time: 00:47
 */
@Component("statementInterceptingAdvice")
public class SInterceptingAdvice implements InterceptingAdvice<Statement> {

    @Autowired
    private StatementBuilder statementBuilder;

    @Override
    public Statement invoke( final MethodInvocation methodInvocation ) throws PreparedStatementInterceptException {
        try {
            Statement statement = (Statement) methodInvocation.proceed();
            return statementBuilder.build( statement );
        }
        catch ( Throwable throwable ) {
            throw new PreparedStatementInterceptException( throwable );
        }
    }


}
