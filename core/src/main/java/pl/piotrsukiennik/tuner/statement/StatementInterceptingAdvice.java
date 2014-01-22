package pl.piotrsukiennik.tuner.statement;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.stereotype.Component;
import pl.piotrsukiennik.tuner.IDecisionService;
import pl.piotrsukiennik.tuner.exception.PreparedStatementInterceptException;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Author: Piotr Sukiennik
 * Date: 09.07.13
 * Time: 00:47
 */
@Component
public class StatementInterceptingAdvice implements InterceptingAdvice<PreparedStatement> {

    @Resource
    private
    IDecisionService decisionService;


    @Override
    public PreparedStatement invoke( final MethodInvocation methodInvocation ) throws PreparedStatementInterceptException {
        String queryString = (String) methodInvocation.getArguments()[0];
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = (PreparedStatement) methodInvocation.proceed();
        }
        catch ( Throwable throwable ) {
            throw new PreparedStatementInterceptException( throwable );
        }
        return decisionService.proceed( preparedStatement, (Connection) methodInvocation.getThis(), queryString );

    }

}
