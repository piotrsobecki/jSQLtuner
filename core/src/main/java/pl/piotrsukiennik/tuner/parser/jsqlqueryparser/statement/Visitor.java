package pl.piotrsukiennik.tuner.parser.jsqlqueryparser.statement;

import pl.piotrsukiennik.tuner.parser.jsqlqueryparser.ParserUtils;
import pl.piotrsukiennik.tuner.persistance.model.query.Query;
import pl.piotrsukiennik.tuner.query.QueryContextManager;


/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 22:53
 */
public abstract class Visitor {
    protected QueryContextManager queryContextManager;
    protected ParserUtils parserUtils;
    protected Visitor(QueryContextManager queryContextManager) {
        this.queryContextManager = queryContextManager;
        this.parserUtils = new ParserUtils(queryContextManager);
    }


    public QueryContextManager getQueryContextManager() {
        return queryContextManager;
    }

}
