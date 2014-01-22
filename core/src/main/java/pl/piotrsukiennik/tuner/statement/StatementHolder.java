package pl.piotrsukiennik.tuner.statement;

import pl.piotrsukiennik.tuner.model.query.Query;

import java.sql.Statement;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Author: Piotr Sukiennik
 * Date: 08.07.13
 * Time: 18:19
 */
public class StatementHolder {
    private static AtomicInteger atomicInteger = new AtomicInteger( 0 );

    private Integer id;

    private String sql;

    private Statement statement;

    private Query query;

    private long timestamp = System.currentTimeMillis();

    public StatementHolder( Integer id ) {
        this.id = id;
    }

    public StatementHolder( String sql, Statement statement ) {
        this.id = atomicInteger.incrementAndGet();
        this.sql = sql;
        this.statement = statement;
    }

    public String getSql() {
        return sql;
    }

    public void setSql( String sql ) {
        this.sql = sql;
    }

    public Statement getStmtRef() {
        return statement;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public Query getQuery() {
        return query;
    }

    public void setQuery( Query query ) {
        this.query = query;
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o )
            return true;
        if ( o == null || getClass() != o.getClass() )
            return false;

        StatementHolder that = (StatementHolder) o;

        if ( id != null ? !id.equals( that.id ) : that.id != null )
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
