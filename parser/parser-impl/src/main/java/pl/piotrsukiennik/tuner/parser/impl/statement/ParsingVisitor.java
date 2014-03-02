package pl.piotrsukiennik.tuner.parser.impl.statement;

import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.parser.QueryParsingContext;


/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 22:53
 */
public abstract class ParsingVisitor<T extends Query> extends Visitor {
    protected T query;

    protected ParsingVisitor( QueryParsingContext parsingContext ) {
        super( parsingContext );
    }

    public ParsingVisitor( QueryParsingContext parsingContext, T query ) {
        super( parsingContext );
        this.query = query;
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
