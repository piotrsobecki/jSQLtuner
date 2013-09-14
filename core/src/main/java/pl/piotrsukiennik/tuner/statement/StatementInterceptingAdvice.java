package pl.piotrsukiennik.tuner.statement;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.framework.ReflectiveMethodInvocation;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import pl.piotrsukiennik.tuner.ai.DecisionServiceImpl;
import pl.piotrsukiennik.tuner.ai.PreparedStatementWrapper;
import pl.piotrsukiennik.tuner.parser.IQuery;
import pl.piotrsukiennik.tuner.parser.IQueryParser;
import pl.piotrsukiennik.tuner.util.Statements;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.sql.RowSet;
import javax.sql.rowset.CachedRowSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Author: Piotr Sukiennik
 * Date: 09.07.13
 * Time: 00:47
 */
@Component
public class StatementInterceptingAdvice implements InterceptingAdvice<PreparedStatement> {

    private @Resource
    DecisionServiceImpl decisionService;


    @Override
    public PreparedStatement invoke(final MethodInvocation methodInvocation) throws Throwable {
        String queryString = (String) methodInvocation.getArguments()[0];
        return decisionService.proceed((PreparedStatement)methodInvocation.proceed(),(Connection) (((ReflectiveMethodInvocation)methodInvocation)).getThis(),queryString);
    }

}
