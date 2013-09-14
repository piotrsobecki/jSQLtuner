package pl.piotrsukiennik.tuner.statement.advisor;

import org.aopalliance.intercept.MethodInvocation;
import pl.piotrsukiennik.tuner.datasources.DataSourcesManager;
import pl.piotrsukiennik.tuner.datasources.shard.IShardingManager;
import pl.piotrsukiennik.tuner.persistance.model.query.WriteQuery;

import javax.annotation.Resource;

/**
 * Author: Piotr Sukiennik
 * Date: 28.07.13
 * Time: 15:58
 */
public class WriteQueryAdvice extends QueryAdvice<WriteQuery,Object> {
    private DataSourcesManager manager;

    public WriteQueryAdvice(DataSourcesManager dataSourcesManager, WriteQuery query) {
        super(query);
        this.manager=dataSourcesManager;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Object ret =  methodInvocation.proceed();
        Integer rowsAffected = (Integer)ret;
        query.setRowsAffected(rowsAffected);
        if (rowsAffected>0){
            manager.invalidate(query);
        }
        return ret;
    }
}
