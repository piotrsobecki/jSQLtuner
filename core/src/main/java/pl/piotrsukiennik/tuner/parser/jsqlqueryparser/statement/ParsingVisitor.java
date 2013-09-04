package pl.piotrsukiennik.tuner.parser.jsqlqueryparser.statement;

import net.sf.jsqlparser.statement.Statement;
import pl.piotrsukiennik.tuner.parser.IQuery;
import pl.piotrsukiennik.tuner.persistance.model.query.Query;
import pl.piotrsukiennik.tuner.query.QueryContextManager;


/**
 * Author: Piotr Sukiennik
 * Date: 26.07.13
 * Time: 22:53
 */
public abstract class ParsingVisitor<T extends Query> {
    protected T query;
    protected QueryContextManager queryContextManager;

    protected ParsingVisitor(QueryContextManager queryContextManager) {
        this.queryContextManager = queryContextManager;
    }
    public ParsingVisitor(QueryContextManager queryContextManager, T query) {
        this.query = query;
        this.queryContextManager = queryContextManager;
    }


    public QueryContextManager getQueryContextManager() {
        return queryContextManager;
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
