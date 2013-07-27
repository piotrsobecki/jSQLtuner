package pl.piotrsukiennik.tuner.statement;

import com.sun.rowset.CachedRowSetImpl;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import pl.piotrsukiennik.tuner.parser.IQuery;
import pl.piotrsukiennik.tuner.parser.IQueryParser;
import pl.piotrsukiennik.tuner.parser.jsqlqueryparser.JSqlQueryParser;

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
public class StatementInterceptingAdvice extends InterceptingAdvice<PreparedStatement> {
    private IQueryParser parser = new JSqlQueryParser();

    @Inject
    @Qualifier("connectionQualifier")
    private List<Advisor> advisors;

    @Override
    public PreparedStatement invoke(MethodInvocation methodInvocation) throws Throwable {
        IQuery query = parser.parse((String) methodInvocation.getArguments()[0]);
        if (checkIfProceed(query)){

            return attachAdvisors((PreparedStatement) methodInvocation.proceed());
        }else {
            return doInstead(query);
        }
    }

    public boolean checkIfProceed(IQuery query) {

        return true;
    }


    public PreparedStatement doInstead(IQuery query) {
        return null;
    }


    public PreparedStatement attachAdvisors(PreparedStatement source){
        ProxyFactory proxyFactory = new ProxyFactory(source);
        for (Advisor adv : this.advisors) {
            proxyFactory.addAdvisor(adv);
        }
        return  (PreparedStatement) proxyFactory.getProxy();
    }


    public CachedRowSet cache(RowSet rowSet){
        try {
            CachedRowSetImpl cachedRowSet = new CachedRowSetImpl();
            cachedRowSet.populate(cachedRowSet);
            return cachedRowSet;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
