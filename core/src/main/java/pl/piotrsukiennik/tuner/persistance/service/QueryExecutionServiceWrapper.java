package pl.piotrsukiennik.tuner.persistance.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Component;
import pl.piotrsukiennik.tuner.datasources.DataRetrieval;
import pl.piotrsukiennik.tuner.datasources.IDataSource;
import pl.piotrsukiennik.tuner.persistance.model.query.SelectQuery;
import pl.piotrsukiennik.tuner.persistance.model.query.execution.DataSource;
import pl.piotrsukiennik.tuner.persistance.model.query.execution.QueryForDataSource;
import pl.piotrsukiennik.tuner.statement.LocalDataSourceMapper;
import pl.piotrsukiennik.tuner.util.holder.ServicesHolder;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Collection;
import java.util.Iterator;

/**
 * Author: Piotr Sukiennik
 * Date: 01.09.13
 * Time: 12:11
 */
@Component
public class QueryExecutionServiceWrapper  {

    private @Resource ServicesHolder servicesHolder;
    private @Resource LocalDataSourceMapper dataSourceMapper;




    private LoadingCache<String,IDataSource> cache = CacheBuilder.<String,IDataSource>newBuilder().
            build(new CacheLoader<String, IDataSource>() {
                @Override
                public IDataSource load(String key) throws Exception {
                    return getLocal(servicesHolder.getQueryExecutionService().getDataSource(key));
                }
            });


    public Collection<QueryForDataSource> submit(SelectQuery query,
                                                 DataRetrieval dataRetrieval) {

        return servicesHolder.getQueryExecutionService().submit(query,
                getDatabase(dataRetrieval.getDataSource()),
                dataRetrieval.getExecutionTimeMillis(),
                dataRetrieval.getRows());
    }

    protected DataSource getDatabase(IDataSource dataSource){
        return servicesHolder.getQueryExecutionService().getDataSource(dataSource.getMetaData().getIdentifier());
    }
    protected IDataSource getLocal(DataSource dataSource){
        Collection<IDataSource> collection = dataSourceMapper.getLocal(dataSource);
        Iterator<IDataSource> iterator = collection.iterator();
        if (iterator.hasNext()){
            IDataSource dataSourceLocal =  iterator.next();
            dataSourceLocal.setPersistedDataSource(dataSource);
            return dataSourceLocal;
        } else {
            return null;
        }
    }

    public IDataSource getDataSourceForQuery(SelectQuery selectQuery) {
        return getLocal(servicesHolder.getQueryExecutionService().getDataSourceForQuery(selectQuery));
    }


    public IDataSource getDataSource(String identifier) {
        return cache.getUnchecked(identifier);
    }

}
