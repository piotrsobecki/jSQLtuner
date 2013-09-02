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

import java.util.Collection;

/**
 * Author: Piotr Sukiennik
 * Date: 31.08.13
 * Time: 19:59
 */
@Service(value = "QueryExecutionService")
@Transactional(readOnly = false)
public class QueryExecutionServiceImpl extends AbstractService implements IQueryExecutionService {

    private class PredicateQueryDataSource implements  Predicate<QueryForDataSource>{

        private Query query;
        private DataSource dataSource;

        private PredicateQueryDataSource(Query query, DataSource dataSource) {
            this.query = query;
            this.dataSource = dataSource;
        }

        @Override
        public boolean apply(QueryForDataSource queryForDataSource) {
            return queryForDataSource.getQuery().equals(query) && queryForDataSource.getDataSource().equals(dataSource);
        }
    }


    private Multimap<Query,QueryForDataSource> sourceMultiValueMap = HashMultimap.create();

    @Override
    public DataSource getDataSourceForQuery(SelectQuery selectQuery) {
        return (DataSource) s().getNamedQuery(QueryForDataSource.GET_DATASOURCE_FOR_QUERY).setString("queryHash",selectQuery.getHash()).setDouble("random",Math.random()).setMaxResults(1).uniqueResult();
    }
    @Override
    public DataSource getDataSource(String identifier) {
        Session session = s();
        DataSource dataSource =  (DataSource) session.createCriteria(DataSource.class).add(Restrictions.eq("identifier",identifier)).setMaxResults(1).uniqueResult();
        if (dataSource==null){
            dataSource=new DataSource();
            dataSource.setIdentifier(identifier);
            session.save(dataSource);
            session.flush();
            session.refresh(dataSource);
        }
        return dataSource;
    }

    public void refreshRoulette(Query query){
       s().getNamedQuery(QueryForDataSource.UPDATE_QUERY_EXECUTION_SETTINGS).setLong("queryId", query.getId()).executeUpdate();
    }

    public  Collection<QueryForDataSource> submit(final SelectQuery query,final  DataSource dataSource, long executionTimeMillis, long rows){
       Collection<QueryForDataSource> queryForDataSourceCollection =  sourceMultiValueMap.get(query);
       Collection<QueryForDataSource> filtered = Collections2.filter(queryForDataSourceCollection,new PredicateQueryDataSource(query,dataSource));
        if (filtered.isEmpty()){
            QueryForDataSource queryForDataSource = new QueryForDataSource();
            queryForDataSource.setAverageRows(rows);
            queryForDataSource.setExecutions(1);
            queryForDataSource.setAverageExecutionTimeMillis(executionTimeMillis);
            queryForDataSource.setDataSource(dataSource);
            queryForDataSource.setQuery(query);
            Session session = s();
            session.save(queryForDataSource);
            session.flush();
            refreshRoulette(query);
            filtered.add(queryForDataSource);
            sourceMultiValueMap.put(query,queryForDataSource);
        }

        for (QueryForDataSource queryForDataSource : filtered) {
            long executions = queryForDataSource.getExecutions();
            float averageExecution = queryForDataSource.getAverageExecutionTimeMillis();
            float averageRows = queryForDataSource.getAverageRows();
            averageRows = (averageRows * executions + rows)/(executions +1);
            averageExecution = (averageExecution* executions + rows)/(executions+1);
            queryForDataSource.setExecutions(executions+1);
            queryForDataSource.setAverageExecutionTimeMillis(averageExecution);
            queryForDataSource.setAverageRows(averageRows);
        }
        return filtered;
    }

}
