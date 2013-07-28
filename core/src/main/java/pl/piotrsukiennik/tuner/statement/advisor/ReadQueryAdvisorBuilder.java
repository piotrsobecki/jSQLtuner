package pl.piotrsukiennik.tuner.statement.advisor;

import org.springframework.aop.Advisor;
import pl.piotrsukiennik.tuner.persistance.model.query.ReadQuery;

/**
 * Author: Piotr Sukiennik
 * Date: 28.07.13
 * Time: 16:16
 */
public class ReadQueryAdvisorBuilder<Q extends ReadQuery> extends QueryAdvisorBuilder<Q> {
    @Override
    public Advisor createAdvisor(Q readQuery) {
        return super.createAdvisor(new ReadQueryAdvice(readQuery));
    }
}
