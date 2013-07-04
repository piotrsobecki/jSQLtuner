package pl.piotrsukiennik.tuner.execution;

import pl.piotrsukiennik.tuner.parser.IQuery;

/**
 * Author: Piotr Sukiennik
 * Date: 30.06.13
 * Time: 17:31
 */
public class QueryExecution {
    private IQuery query;
    private String queryHash;
    private Long   queryExecutionTime;

     QueryExecution(IQuery query, String queryHash, Long queryExecutionTime) {
        this.query = query;
        this.queryHash = queryHash;
        this.queryExecutionTime = queryExecutionTime;
    }


    public IQuery getQuery() {
        return query;
    }

    public String getQueryHash() {
        return queryHash;
    }

    public Long getQueryExecutionTime() {
        return queryExecutionTime;
    }
}
