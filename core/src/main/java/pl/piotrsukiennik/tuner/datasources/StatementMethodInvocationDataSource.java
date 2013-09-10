package pl.piotrsukiennik.tuner.datasources;

import org.aopalliance.intercept.MethodInvocation;
import pl.piotrsukiennik.tuner.persistance.model.query.Query;
import pl.piotrsukiennik.tuner.persistance.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.persistance.model.query.SelectQuery;

import java.io.Serializable;
import java.sql.*;

/**
 * Author: Piotr Sukiennik
 * Date: 26.08.13
 * Time: 23:01
 */
public class StatementMethodInvocationDataSource extends AbstractDataSource {

    private Statement statement;
    private MethodInvocation methodInvocation;
    private ReadQuery readQuery;
    public StatementMethodInvocationDataSource(Statement statement,
                                               MethodInvocation methodInvocation,
                                               ReadQuery readQuery) throws SQLException {
        super(new ConnectionDataSourceMetaData(statement.getConnection()));
        this.statement = statement;
        this.methodInvocation=methodInvocation;
        this.readQuery=readQuery;
    }

    @Override
    protected ResultSet get(ReadQuery query) throws Throwable {
        return (ResultSet)methodInvocation.proceed();
    }

    @Override
    public void putData(ReadQuery query, Serializable resultSet) {
    }

    public ReadQuery getReadQuery() {
        return readQuery;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o instanceof StatementMethodInvocationDataSource){
            ReadQuery oQ = ((StatementMethodInvocationDataSource) o).getReadQuery();
            return ((getReadQuery().getId()==oQ.getId()) && oQ.getId()!=0)
                    || getReadQuery().getHash().equals(oQ.getHash());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + readQuery.hashCode();
        return result;
    }
}
