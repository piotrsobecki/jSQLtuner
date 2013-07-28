package pl.piotrsukiennik.tuner.statement.advisor;

import org.aopalliance.intercept.MethodInvocation;
import pl.piotrsukiennik.tuner.persistance.model.query.ReadQuery;

import java.sql.ResultSet;

/**
 * Author: Piotr Sukiennik
 * Date: 28.07.13
 * Time: 15:58
 */
public class ReadQueryAdvice extends QueryAdvice<ReadQuery,ResultSet> {

    public ReadQueryAdvice(ReadQuery query) {
        super(query);
    }

    @Override
    public ResultSet invoke(MethodInvocation methodInvocation) throws Throwable {
        ResultSet ret =  (ResultSet) methodInvocation.proceed();
        return ret;
    }
}
