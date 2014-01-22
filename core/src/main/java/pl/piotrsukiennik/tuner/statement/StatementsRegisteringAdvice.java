package pl.piotrsukiennik.tuner.statement;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.sql.Statement;

@Component
public class StatementsRegisteringAdvice implements AfterReturningAdvice {

    private
    @Resource
    StatementsMonitor statementsMonitor;

    @Override
    public void afterReturning( final Object returnValue, final Method method, final Object[] args, final Object target )
     throws Throwable {
        if ( returnValue instanceof Statement ) {
            Statement stmt = Statement.class.cast( returnValue );
            this.statementsMonitor.add( stmt, args != null && args.length > 0 ? args[0].toString() : null );
        }
    }
}
