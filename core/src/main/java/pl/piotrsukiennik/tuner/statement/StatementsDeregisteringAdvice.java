package pl.piotrsukiennik.tuner.statement;

import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.sql.Connection;

@Component
public class StatementsDeregisteringAdvice implements MethodBeforeAdvice {


    private
    @Resource
    StatementsMonitor statementsMonitor;

    @Override
    public void before( final Method method, final Object[] args, final Object target ) throws Throwable {
        if ( "close".equals( method.getName() ) ) {
            statementsMonitor.remove( Connection.class.cast( target ) );
        }
    }
}
