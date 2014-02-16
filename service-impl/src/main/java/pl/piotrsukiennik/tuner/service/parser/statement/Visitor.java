package pl.piotrsukiennik.tuner.service.parser.statement;

import pl.piotrsukiennik.tuner.service.QueryContext;
import pl.piotrsukiennik.tuner.service.parser.QueryElementParserService;


/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 22:53
 */
public abstract class Visitor {
    protected QueryContext queryContext;

    protected QueryElementParserService queryElementParserService;

    protected Visitor( QueryElementParserService queryElementParserService, QueryContext queryContext ) {
        this.queryContext = queryContext;
        this.queryElementParserService = queryElementParserService;
    }


    public QueryContext getQueryContext() {
        return queryContext;
    }

}
