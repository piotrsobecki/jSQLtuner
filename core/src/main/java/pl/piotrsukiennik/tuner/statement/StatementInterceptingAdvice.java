package pl.piotrsukiennik.tuner.statement;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ReflectiveMethodInvocation;
import org.springframework.stereotype.Component;
import pl.piotrsukiennik.tuner.IDecisionService;

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

    private
    @Resource
    IDecisionService decisionService;


    @Override
    public PreparedStatement invoke( final MethodInvocation methodInvocation ) throws Throwable {
        String queryString = (String) methodInvocation.getArguments()[0];
        return decisionService.proceed( (PreparedStatement) methodInvocation.proceed(), (Connection) ( ( (ReflectiveMethodInvocation) methodInvocation ) ).getThis(), queryString );
    }

}
