package pl.piotrsukiennik.tuner.statement;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.stereotype.Component;

/**
 * Author: Piotr Sukiennik
 * Date: 09.07.13
 * Time: 00:47
 */
@Component
public class InnerStatementInterceptingAdvice implements MethodInterceptor {
    @Override
    public Object invoke( MethodInvocation methodInvocation ) throws Throwable {
        Object ret = methodInvocation.proceed();
        return ret;
    }
}
