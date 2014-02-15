package pl.piotrsukiennik.tuner.statement.advisor;

import org.springframework.aop.Advisor;
import pl.piotrsukiennik.tuner.model.query.WriteQuery;

/**
 * Author: Piotr Sukiennik
 * Date: 28.07.13
 * Time: 16:18
 */
public class WriteQueryAdvisorBuilder<Q extends WriteQuery> extends QueryAdvisorBuilder<Q> {
    @Override
    public Advisor createAdvisor( Q writeQuery ) {
        return super.createAdvisor( new WriteQueryAdvice( sharder, writeQuery ) );
    }
}
