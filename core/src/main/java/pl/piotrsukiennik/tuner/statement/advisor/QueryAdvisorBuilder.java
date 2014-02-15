package pl.piotrsukiennik.tuner.statement.advisor;

import org.aopalliance.aop.Advice;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import pl.piotrsukiennik.tuner.Sharder;
import pl.piotrsukiennik.tuner.model.query.Query;

import javax.annotation.Resource;

/**
 * Author: Piotr Sukiennik
 * Date: 28.07.13
 * Time: 15:56
 */
public abstract class QueryAdvisorBuilder<Q extends Query> {

    @Resource
    protected Sharder sharder;

    private String expression;

    private Integer order;

    public String getExpression() {
        return expression;
    }

    public void setExpression( String expression ) {
        this.expression = expression;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder( Integer order ) {
        this.order = order;
    }

    public abstract Advisor createAdvisor( Q query );

    protected Advisor createAdvisor( Advice advice ) {
        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
        advisor.setExpression( expression );
        advisor.setAdvice( advice );
        advisor.setOrder( order );
        return advisor;
    }
}
