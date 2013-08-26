package pl.piotrsukiennik.tuner.datasources;

import org.aopalliance.intercept.MethodInvocation;
import pl.piotrsukiennik.tuner.persistance.model.query.Query;

import java.sql.ResultSet;

/**
 * Author: Piotr Sukiennik
 * Date: 26.08.13
 * Time: 23:01
 */
public class MethodInvocationDataSource extends AbstractDataSource {

    private MethodInvocation methodInvocation;


    public MethodInvocationDataSource(IDataSourceMetaData dataSourceMetaData, MethodInvocation methodInvocation) {
        super(dataSourceMetaData);
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
