package pl.piotrsukiennik.tuner.statement;

import java.lang.reflect.Method;
import java.sql.Connection;

import javax.inject.Inject;

import org.springframework.aop.MethodBeforeAdvice;

public class StatementsDeregisteringAdvice implements MethodBeforeAdvice {

@Inject
private StatementsMonitor statementsMonitor;

@Override
public void before(final Method method, final Object[] args, final Object target) throws Throwable {
    if (method.getName().equals("close")) {
        statementsMonitor.remove(Connection.class.cast(target));
    }
    }
}