package pl.piotrsukiennik.tuner.statement.advisor;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import pl.piotrsukiennik.tuner.persistance.model.query.Query;
import pl.piotrsukiennik.tuner.statement.InterceptingAdvice;

/**
 * Author: Piotr Sukiennik
 * Date: 28.07.13
 * Time: 15:55
 */
public abstract class QueryAdvice<Q extends Query,O extends Object> implements InterceptingAdvice<O> {

    private Q query;

    public QueryAdvice(Q query) {
        this.query = query;
    }

    public Q getQuery() {
        return query;
    }


}
