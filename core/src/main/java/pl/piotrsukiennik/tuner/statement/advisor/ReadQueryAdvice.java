package pl.piotrsukiennik.tuner.statement.advisor;

import org.aopalliance.intercept.MethodInvocation;
import pl.piotrsukiennik.tuner.datasources.*;
import pl.piotrsukiennik.tuner.persistance.model.query.ReadQuery;

import java.sql.ResultSet;

/**
 * Author: Piotr Sukiennik
 * Date: 28.07.13
 * Time: 15:58
 */
public class ReadQueryAdvice extends QueryAdvice<ReadQuery,ResultSet> {

    private DataSourcesManager manager;


    public ReadQueryAdvice(DataSourcesManager dataSourcesManager, ReadQuery query) {
        super(query);
        this.manager = dataSourcesManager;
    }

    @Override
    public ResultSet invoke(final MethodInvocation methodInvocation) throws Throwable {
        manager.setDataForQuery(query,new StatementMethodInvocationDataSource(methodInvocation));
        return manager.getData(query);
    }


    public DataSourcesManager getManager() {
        return manager;
    }

    public void setManager(DataSourcesManager manager) {
        this.manager = manager;
    }
}
