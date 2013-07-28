package pl.piotrsukiennik.tuner.statement;

import java.lang.reflect.Method;
import java.sql.Connection;

import javax.inject.Inject;

import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.stereotype.Component;

@Component
public class StatementsDeregisteringAdvice implements MethodBeforeAdvice {

@Inject
private StatementsMonitor statementsMonitor;

@Override
public void before(final Method method, final Object[] args, final Object target) throws Throwable {
    if ("close".equals(method.getName())) {
        statementsMonitor.remove(Connection.class.cast(target));
    }
    }
}