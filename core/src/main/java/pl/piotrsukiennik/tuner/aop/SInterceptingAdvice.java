package pl.piotrsukiennik.tuner.aop;

import org.aopalliance.intercept.MethodInvocation;
import pl.piotrsukiennik.tuner.statement.StatementBuilder;
import pl.piotrsukiennik.tuner.exception.PreparedStatementInterceptException;

import java.sql.Statement;


/**
 * Author: Piotr Sukiennik
 * Date: 09.07.13
 * Time: 00:47
 */
public class SInterceptingAdvice implements InterceptingAdvice<Statement> {

    private StatementBuilder statementBuilder;

    public SInterceptingAdvice( StatementBuilder statementBuilder ) {
        this.statementBuilder = statementBuilder;
    }

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
