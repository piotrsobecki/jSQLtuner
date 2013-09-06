package pl.piotrsukiennik.tuner.persistance.service.transactional;

import com.google.common.base.Predicate;
import com.google.common.collect.*;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.piotrsukiennik.tuner.persistance.model.query.Query;
import pl.piotrsukiennik.tuner.persistance.model.query.SelectQuery;
import pl.piotrsukiennik.tuner.persistance.model.query.execution.DataSource;
import pl.piotrsukiennik.tuner.persistance.model.query.execution.QueryForDataSource;
import pl.piotrsukiennik.tuner.persistance.service.AbstractService;
import pl.piotrsukiennik.tuner.persistance.service.IQueryExecutionService;
import pl.piotrsukiennik.tuner.persistance.service.IQueryService;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Random;

/**
 * Author: Piotr Sukiennik
 * Date: 31.08.13
 * Time: 19:59
 */
@Service(value = "QueryExecutionService")
@Transactional(readOnly = false)
public class QueryExecutionServiceImpl extends AbstractService implements IQueryExecutionService {


    private @Resource IQueryService queryService;

    private class PredicateQueryDataSource implements  Predicate<QueryForDataSource>{


        private DataSource dataSource;

        private PredicateQueryDataSource(DataSource dataSource) {

            this.dataSource = dataSource;
        }

        @Override
        public boolean apply(QueryForDataSource queryForDataSource) {
            return  queryForDataSource.getDataSource().equals(dataSource);
        }
    }


    private Multimap<String,QueryForDataSource> sourceMultiValueMap = HashMultimap.create();

    @Override
    public DataSource getDataSourceForQuery(SelectQuery selectQuery) {
        float random= (float)Math.random();
        DataSource dataSource =  (DataSource) s().getNamedQuery(QueryForDataSource.GET_DATASOURCE_FOR_QUERY)
                .setString("queryHash",selectQuery.getHash())
                .setFloat("random", random).setMaxResults(1).uniqueResult();
        return dataSource;
    }
    @Override
    public DataSource getDataSource(Class clazz, String identifier) {
        Session session = s();
        String clazzStr = clazz.getName();
        DataSource dataSource =  (DataSource) session.createCriteria(DataSource.class)
                .add(Restrictions.eq("clazz",clazzStr))
                .add(Restrictions.eq("identifier",identifier)).setMaxResults(1).uniqueResult();
        if (dataSource==null){
            dataSource=new DataSource();
            dataSource.setIdentifier(identifier);
            dataSource.setClazz(clazzStr);
            session.save(dataSource);
            session.flush();
            session.refresh(dataSource);
        }
        return dataSource;
    }

    public void refreshRoulette(Query query){
       s().getNamedQuery(QueryForDataSource.UPDATE_QUERY_EXECUTION_SETTINGS).setString("queryHash", query.getHash()).executeUpdate();
    }

    public void removeDataSourceForQuery(final SelectQuery query,final  DataSource dataSource){
      s().getNamedQuery(QueryForDataSource.REMOVE_DATASOURCE_FOR_QUERY).setString("queryHash",query.getHash()).setEntity("dataSource",dataSource).executeUpdate();
    }
    public QueryForDataSource getQueryForDataSource(final SelectQuery q,final  DataSource dataSource){
        return (QueryForDataSource) s().createCriteria(QueryForDataSource.class)
                .add(Restrictions.eq("query", q)).add(Restrictions.eq("dataSource", dataSource)).uniqueResult();
    }
    public  QueryForDataSource submitNewDataSourceForQuery(final SelectQuery q,final  DataSource dataSource){
        Session session = s();
        SelectQuery persistedQuery = queryService.submit(q);
        QueryForDataSource queryForDataSource = getQueryForDataSource(persistedQuery,dataSource);
        if (queryForDataSource==null){
            queryForDataSource = new QueryForDataSource();
            queryForDataSource.setAverageRows(-1);
            queryForDataSource.setExecutions(0);
            queryForDataSource.setAverageExecutionTimeNano(-1);
            queryForDataSource.setDataSource(dataSource);
            queryForDataSource.setQuery(persistedQuery);
            session.save(queryForDataSource);
            session.flush();
            refreshRoulette(persistedQuery);
        }  else {
            queryForDataSource.setAverageRows(-1);
            queryForDataSource.setExecutions(0);
            queryForDataSource.setAverageExecutionTimeNano(-1);
            queryForDataSource.setDataSource(dataSource);
            queryForDataSource.setQuery(persistedQuery);
            session.merge(queryForDataSource);
            session.flush();
            refreshRoulette(persistedQuery);
        }
        sourceMultiValueMap.put(persistedQuery.getHash(),queryForDataSource);
        return queryForDataSource;
    }
    public  Collection<QueryForDataSource> submit(final SelectQuery q,final  DataSource dataSource, long executionTimeNano, long rows){
        SelectQuery persistedQuery = queryService.submit(q);
        Collection<QueryForDataSource> queryForDataSourceCollection =  sourceMultiValueMap.get(persistedQuery.getHash());
        Collection<QueryForDataSource> filtered = Collections2.filter(queryForDataSourceCollection,new PredicateQueryDataSource(dataSource));
        Session session = s();

        if (filtered.isEmpty()){
            QueryForDataSource queryForDataSource = new QueryForDataSource();
            queryForDataSource.setAverageRows(rows);
            queryForDataSource.setExecutions(1);
            queryForDataSource.setAverageExecutionTimeNano(executionTimeNano);
            queryForDataSource.setDataSource(dataSource);
            queryForDataSource.setQuery(persistedQuery);
            session.save(queryForDataSource);
            session.flush();
            refreshRoulette(persistedQuery);
            filtered.add(queryForDataSource);
            sourceMultiValueMap.put(persistedQuery.getHash(),queryForDataSource);
        } else {
            for (QueryForDataSource queryForDataSource : filtered) {
                long executions = queryForDataSource.getExecutions();
                float averageExecution = queryForDataSource.getAverageExecutionTimeNano();
                float averageRows = queryForDataSource.getAverageRows();
                averageRows = (averageRows * executions + rows)/(executions +1);
                averageExecution = (averageExecution* executions + executionTimeNano)/(executions+1);
                queryForDataSource.setExecutions(executions+1);
                queryForDataSource.setAverageExecutionTimeNano(averageExecution);
                queryForDataSource.setAverageRows(averageRows);
                session.merge(queryForDataSource);
                session.flush();
                refreshRoulette(persistedQuery);
            }
        }



        return filtered;
    }



}
