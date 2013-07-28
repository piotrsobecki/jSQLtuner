package pl.piotrsukiennik.tuner.statement.advisor;

import org.aopalliance.intercept.MethodInvocation;
import pl.piotrsukiennik.tuner.persistance.model.query.WriteQuery;

/**
 * Author: Piotr Sukiennik
 * Date: 28.07.13
 * Time: 15:58
 */
public class WriteQueryAdvice extends QueryAdvice<WriteQuery,Object> {

    public WriteQueryAdvice(WriteQuery query) {
        super(query);
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Object ret =  methodInvocation.proceed();

        return ret;
    }
}
