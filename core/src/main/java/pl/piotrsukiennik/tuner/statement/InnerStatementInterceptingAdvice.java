package pl.piotrsukiennik.tuner.statement;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ReflectiveMethodInvocation;
import org.springframework.stereotype.Component;
import pl.piotrsukiennik.tuner.ai.DecisionServiceImpl;
import pl.piotrsukiennik.tuner.parser.IQueryParser;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Author: Piotr Sukiennik
 * Date: 09.07.13
 * Time: 00:47
 */
@Component
public class InnerStatementInterceptingAdvice implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Object ret =  methodInvocation.proceed();
        return ret;
    }
}
