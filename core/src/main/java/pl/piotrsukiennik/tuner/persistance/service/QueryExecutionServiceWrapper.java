package pl.piotrsukiennik.tuner.persistance.service;

import org.springframework.stereotype.Component;
import pl.piotrsukiennik.tuner.datasources.DataRetrieval;
import pl.piotrsukiennik.tuner.datasources.IDataSource;
import pl.piotrsukiennik.tuner.persistance.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.persistance.model.query.execution.DataSource;
import pl.piotrsukiennik.tuner.persistance.model.query.execution.QueryForDataSource;
import pl.piotrsukiennik.tuner.statement.LocalDataSourceMapper;
import pl.piotrsukiennik.tuner.util.holder.ServicesHolder;

import javax.annotation.Resource;
import java.util.ArrayList;
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
    public void removePossibleDataSources(Collection<? extends ReadQuery> queries,
                                          Collection<? extends IDataSource> dataSources) {
        Collection<DataSource> persistedSources = new ArrayList<DataSource>();
        for (IDataSource dataSource: dataSources){
            persistedSources.add(getRemote(dataSource));
        }
        servicesHolder.getQueryExecutionService().removeDataSourcesForQueries(queries, persistedSources);
    }
    public void removePossibleDataSources(ReadQuery query,
                                         Collection<? extends IDataSource> dataSources) {
        Collection<DataSource> persistedSources = new ArrayList<DataSource>();
        for (IDataSource dataSource: dataSources){
            persistedSources.add(getRemote(dataSource));
        }
        servicesHolder.getQueryExecutionService().removeDataSourcesForQuery(query, persistedSources);
    }
    public void removePossibleDataSource(ReadQuery query,
                                                       IDataSource dataSource) {

        servicesHolder.getQueryExecutionService().removeDataSourceForQuery(query, getRemote(dataSource));
    }
    public QueryForDataSource submitPossibleDataSource(ReadQuery query,
                                                 IDataSource dataSource) {

        return servicesHolder.getQueryExecutionService().submitNewDataSourceForQuery(query, getRemote(dataSource));
    }
    public Collection<QueryForDataSource> submit(ReadQuery query,
                                                 DataRetrieval dataRetrieval) {

        return servicesHolder.getQueryExecutionService().submit(query,
                getRemote(dataRetrieval.getDataSource()),
                dataRetrieval.getExecutionTimeNano());
    }

    protected DataSource getRemote(IDataSource dataSource){
        return servicesHolder.getQueryExecutionService().getDataSource(dataSource.getClass(),dataSource.getMetaData().getIdentifier());
    }
    protected IDataSource getLocal(ReadQuery selectQuery,DataSource dataSource){
        Collection<IDataSource> collection = dataSourceMapper.getLocal(selectQuery,dataSource);
        Iterator<IDataSource> iterator = collection.iterator();
        if (iterator.hasNext()){
            IDataSource dataSourceLocal =  iterator.next();
            dataSourceLocal.setPersistedDataSource(dataSource);
            return dataSourceLocal;
        } else {
            return null;
        }
    }

    public IDataSource getDataSourceForQuery(ReadQuery selectQuery) {
        return getLocal(selectQuery,servicesHolder.getQueryExecutionService().getDataSourceForQuery(selectQuery));
    }

}
