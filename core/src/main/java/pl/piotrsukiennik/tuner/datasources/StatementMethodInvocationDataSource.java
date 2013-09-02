package pl.piotrsukiennik.tuner.datasources;

import org.aopalliance.intercept.MethodInvocation;
import pl.piotrsukiennik.tuner.persistance.model.query.Query;
import pl.piotrsukiennik.tuner.persistance.model.query.SelectQuery;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Author: Piotr Sukiennik
 * Date: 26.08.13
 * Time: 23:01
 */
public class StatementMethodInvocationDataSource extends AbstractDataSource {

    private Statement statement;
    private MethodInvocation methodInvocation;

    public StatementMethodInvocationDataSource(Statement statement,MethodInvocation methodInvocation) throws SQLException {
        super(new ConnectionDataSourceMetaData(statement.getConnection()));
        this.statement = statement;
        this.methodInvocation=methodInvocation;
    }

    @Override
    protected ResultSet get(SelectQuery query) throws Throwable {
        return (ResultSet)methodInvocation.proceed();
    }

    @Override
    public void putData(SelectQuery query, Serializable resultSet) {
    }
}
