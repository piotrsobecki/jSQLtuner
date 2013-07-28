package pl.piotrsukiennik.tuner.statement;


import com.mysql.jdbc.JDBC4PreparedStatement;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import pl.piotrsukiennik.tuner.persistance.model.query.*;
import pl.piotrsukiennik.tuner.statement.advisor.QueryAdvisorBuilder;
import pl.piotrsukiennik.tuner.statement.advisor.ReadQueryAdvisorBuilder;
import pl.piotrsukiennik.tuner.statement.advisor.WriteQueryAdvisorBuilder;

import javax.inject.Inject;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Author: Piotr Sukiennik
 * Date: 28.07.13
 * Time: 14:49
 */
@Component
public class PreparedStatementProxyCreator {

    @Inject
    @Qualifier("writeQueryAdvisorBuilder")
    private List<WriteQueryAdvisorBuilder<WriteQuery>> writeQueryAdvisors;


    @Inject
    @Qualifier("readQueryAdvisorBuilder")
    private List<ReadQueryAdvisorBuilder<ReadQuery>> readQueryAdvisors;


    public  <PS extends PreparedStatement> PS create(Query query, PS source){
        if (query instanceof ReadQuery){
            return attachAdvisors(readQueryAdvisors,(ReadQuery)query,source);
        }   else if (query instanceof WriteQuery) {
            return attachAdvisors(writeQueryAdvisors, (WriteQuery) query, source);
        }
        return source;
    }



    public <PS extends PreparedStatement,Q extends  Query, QAB extends QueryAdvisorBuilder<Q>> PS attachAdvisors(List<QAB> advisorBuilders, Q query, PS source){
        List<Advisor> advisors = new ArrayList<Advisor>();
        for (QAB advisorContext: advisorBuilders){
            advisors.add(advisorContext.createAdvisor(query));
        }
        return proxy(source,advisors);
    }

    public <PS extends PreparedStatement> PS proxy(PS source, Collection<Advisor> advisors){
        ProxyFactory proxyFactory = new ProxyFactory(source);
        for (Advisor advisor: advisors){
            proxyFactory.addAdvisor(advisor);
        }
        return  (PS) proxyFactory.getProxy();
    }
}
