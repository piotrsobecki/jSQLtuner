package pl.piotrsukiennik.tuner.datasources;

import org.aopalliance.intercept.MethodInvocation;
import pl.piotrsukiennik.tuner.persistance.model.query.Query;

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

    private MethodInvocation methodInvocation;


    public StatementMethodInvocationDataSource(MethodInvocation methodInvocation) throws SQLException {
        super(new ConnectionDataSourceMetaData(((Statement) methodInvocation.getThis()).getConnection()));
        this.methodInvocation = methodInvocation;
    }

    @Override
    protected ResultSet get(Query query) throws Throwable {
        return (ResultSet)methodInvocation.proceed();
    }

    @Override
    public void putData(Query query, ResultSet resultSet) {
    }
}
