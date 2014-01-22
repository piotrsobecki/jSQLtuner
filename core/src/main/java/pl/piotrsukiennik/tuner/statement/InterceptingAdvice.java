package pl.piotrsukiennik.tuner.statement;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public interface InterceptingAdvice<T extends Object> extends MethodInterceptor {
    @Override
    T invoke( MethodInvocation methodInvocation ) throws Throwable;
}
