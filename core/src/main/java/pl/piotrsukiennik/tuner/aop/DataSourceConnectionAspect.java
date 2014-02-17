package pl.piotrsukiennik.tuner.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.sql.Connection;
import java.util.List;

@Component
@Aspect
class DataSourceConnectionAspect {

    public static final String ANY_DATA_SOURCE_EXECUTION = "execution (* javax.sql.DataSource.getConnection())";


    @Inject
    @Qualifier("connectionAdvisor")
    private List<Advisor> advisors;

    public DataSourceConnectionAspect() {
    }

    @Around(value = "onGetConnectionPointcut()")
    public Object onNewConnection( final ProceedingJoinPoint pjp ) throws Throwable {
        Connection retVal = (Connection) pjp.proceed( pjp.getArgs() );
        ProxyFactory proxyFactory = new ProxyFactory( retVal );
        for ( Advisor adv : this.advisors ) {
            proxyFactory.addAdvisor( adv );
        }
        retVal = (Connection) proxyFactory.getProxy();
        return retVal;
    }

    @Pointcut(ANY_DATA_SOURCE_EXECUTION)
    protected void onGetConnectionPointcut() {
    }
} 
