package pl.piotrsukiennik.tuner.service.parser.statement;

import pl.piotrsukiennik.tuner.service.QueryContext;
import pl.piotrsukiennik.tuner.service.parser.ElementParserService;


/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 22:53
 */
public abstract class Visitor {
    protected QueryContext queryContext;

    protected ElementParserService elementParserService;

    protected Visitor( ElementParserService elementParserService, QueryContext queryContext ) {
        this.queryContext = queryContext;
        this.elementParserService = elementParserService;
    }


    public QueryContext getQueryContext() {
        return queryContext;
    }

}
