package pl.piotrsukiennik.tuner.parser.jsqlqueryparser.statement;

import pl.piotrsukiennik.tuner.parser.IQuery;

/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 22:53
 */
public abstract class ParsingVisitor<T extends IQuery> {
    protected T query;

    protected ParsingVisitor() {
    }
    public ParsingVisitor(T query) {
        this.query = query;
    }

    public T getQuery() {
        return query;
    }

    protected void setQuery(T query) {
        this.query = query;
    }

    protected  <T extends Object> void init(T o){
        if (query!=null){
            query.setValue(o.toString());
        }
    }
}
