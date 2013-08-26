package pl.piotrsukiennik.tuner.statement.advisor;

import org.aopalliance.intercept.MethodInvocation;
import pl.piotrsukiennik.tuner.datasources.*;
import pl.piotrsukiennik.tuner.persistance.model.query.Query;
import pl.piotrsukiennik.tuner.persistance.model.query.ReadQuery;

import javax.annotation.Resource;
import java.sql.ResultSet;

/**
 * Author: Piotr Sukiennik
 * Date: 28.07.13
 * Time: 15:58
 */
public class ReadQueryAdvice extends QueryAdvice<ReadQuery,ResultSet> implements IDataSourceAware {

    private IDataSourceMetaData dataSourceMetaData;

    private @Resource DataSourcesManager manager;


    public ReadQueryAdvice(ReadQuery query) {
        super(query);
    }

    @Override
    public ResultSet invoke(final MethodInvocation methodInvocation) throws Throwable {
        manager.setDataForQuery(query,new MethodInvocationDataSource(dataSourceMetaData,methodInvocation));
        return manager.getData(query);
    }

    @Override
    public IDataSourceMetaData getDataSourceMetaData() {
        return dataSourceMetaData;
    }

    protected void setDataSourceMetaData(IDataSourceMetaData dataSourceMetaData){
        this.dataSourceMetaData = dataSourceMetaData;
    }
}
