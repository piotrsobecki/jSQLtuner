package pl.piotrsukiennik.tuner.service.parser.statement;

import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.service.QueryElementParserService;
import pl.piotrsukiennik.tuner.service.query.QueryContext;


/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 22:53
 */
public abstract class ParsingVisitor<T extends Query> extends Visitor {
    protected T query;

    protected ParsingVisitor( QueryElementParserService queryElementParserService, QueryContext queryContext ) {
        super( queryElementParserService, queryContext );
    }

    public ParsingVisitor( QueryElementParserService queryElementParserService, QueryContext queryContext, T query ) {
        super( queryElementParserService, queryContext );
        this.query = query;
    }


    public QueryContext getQueryContext() {
        return queryContext;
    }

    public T getQuery() {
        return query;
    }

    protected void setQuery( T query ) {
        this.query = query;
    }


    protected <T extends Object> void init( T o ) {
        if ( query != null ) {
            query.setValue( o.toString() );
        }
    }

}
