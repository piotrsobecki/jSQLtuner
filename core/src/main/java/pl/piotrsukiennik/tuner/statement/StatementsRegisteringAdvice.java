package pl.piotrsukiennik.tuner.statement;

import java.sql.Statement;


import java.lang.reflect.Method;

import javax.inject.Inject;

import org.springframework.aop.AfterReturningAdvice;

public class StatementsRegisteringAdvice implements AfterReturningAdvice {

@Inject
private StatementsMonitor statementsMonitor;

@Override
public void afterReturning(final Object returnValue, final Method method, final Object[] args, final Object target)
throws Throwable {
    if (returnValue instanceof Statement) {
        Statement stmt = Statement.class.cast(returnValue);
        this.statementsMonitor.add(stmt, args != null && args.length > 0 ? args[0].toString() : null);
    }
}
}