package pl.piotrsukiennik.tuner.statement;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public abstract class InterceptingAdvice<T> implements MethodInterceptor {


    @Override
    public abstract T invoke(MethodInvocation methodInvocation) throws Throwable;
}