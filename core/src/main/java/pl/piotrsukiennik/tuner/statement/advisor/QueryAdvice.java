package pl.piotrsukiennik.tuner.statement.advisor;

import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.statement.InterceptingAdvice;

/**
 * Author: Piotr Sukiennik
 * Date: 28.07.13
 * Time: 15:55
 */
public abstract class QueryAdvice<Q extends Query, O> implements InterceptingAdvice<O> {

    protected Q query;


    public QueryAdvice( Q query ) {
        this.query = query;
    }

    public Q getQuery() {
        return query;
    }


}
