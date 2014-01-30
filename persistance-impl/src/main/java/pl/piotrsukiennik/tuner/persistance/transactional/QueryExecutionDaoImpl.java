package pl.piotrsukiennik.tuner.persistance.transactional;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.model.query.execution.DataSourceIdentity;
import pl.piotrsukiennik.tuner.model.query.execution.QuerySelectionHelper;
import pl.piotrsukiennik.tuner.persistance.QueryDao;
import pl.piotrsukiennik.tuner.persistance.QueryExecutionDao;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Author: Piotr Sukiennik
 * Date: 31.08.13
 * Time: 19:59
 */
@Repository
@Transactional(value = "jsqlTunerTransactionManager")
class QueryExecutionDaoImpl extends CrudDaoImpl implements QueryExecutionDao {

    @Resource
    private QueryDao queryService;

    private class PredicateQueryDataSource implements Predicate<QuerySelectionHelper> {


        private DataSourceIdentity dataSourceIdentity;

        private PredicateQueryDataSource( DataSourceIdentity dataSourceIdentity ) {

            this.dataSourceIdentity = dataSourceIdentity;
        }

        @Override
        public boolean apply( QuerySelectionHelper querySelectionHelper ) {
            return querySelectionHelper.getDataSourceIdentity().equals( dataSourceIdentity );
        }
    }


    private Multimap<String, QuerySelectionHelper> sourceMultiValueMap = HashMultimap.create();

    @Override
    public DataSourceIdentity getDataSourceForQuery( ReadQuery selectQuery ) {
        float random = (float) Math.random();
        DataSourceIdentity dataSourceIdentity = (DataSourceIdentity) s().getNamedQuery( QuerySelectionHelper.GET_DATASOURCE_FOR_QUERY )
         .setString( "queryHash", selectQuery.getHash() )
         .setFloat( "random", random ).setMaxResults( 1 ).uniqueResult();
        return dataSourceIdentity;
    }

    @Override
    public List<DataSourceIdentity> getDataSourcesForQuery( ReadQuery selectQuery ) {
        return s().getNamedQuery( QuerySelectionHelper.GET_DATASOURCES_FOR_QUERY )
         .setString( "queryHash", selectQuery.getHash() ).list();

    }

    @Override
    public DataSourceIdentity getDataSource( String identifier ) {
        DataSourceIdentity dataSourceIdentity = (DataSourceIdentity) s().createCriteria( DataSourceIdentity.class )
         .add( Restrictions.eq( "identifier", identifier ) ).setMaxResults( 1 ).uniqueResult();
        if ( dataSourceIdentity == null ) {
            dataSourceIdentity = new DataSourceIdentity();
            dataSourceIdentity.setIdentifier( identifier );
            create( dataSourceIdentity );
        }
        return dataSourceIdentity;
    }

    public void refreshRoulette( Query query ) {
        //TODO

    }

    public void removeDataSourceForQuery( final ReadQuery query, final DataSourceIdentity dataSourceIdentity ) {
        Query queryPersisted = queryService.submit( query );
        s().getNamedQuery( QuerySelectionHelper.REMOVE_DATASOURCE_FOR_QUERY ).setEntity( "query", queryPersisted ).setEntity( "dataSource", dataSourceIdentity ).executeUpdate();
        refreshRoulette( query );
    }

    public void removeDataSourcesForQueries( Collection<? extends ReadQuery> queries, final Collection<DataSourceIdentity> dataSourceIdentities ) {
        if ( dataSourceIdentities == null || dataSourceIdentities.isEmpty() || queries == null || queries.isEmpty() ) {
            return;
        }
        Collection<Query> queriesPersisted = new LinkedHashSet<Query>();
        for ( ReadQuery readQuery : queries ) {
            queriesPersisted.add( queryService.submit( readQuery ) );
        }
        s().getNamedQuery( QuerySelectionHelper.REMOVE_DATASOURCES_FOR_QUERIES ).setParameterList( "queries", queriesPersisted ).setParameterList( "dataSources", dataSourceIdentities ).executeUpdate();
        for ( Query query : queriesPersisted ) {
            refreshRoulette( query );
        }
    }

    public void removeDataSourcesForQuery( final ReadQuery query, final Collection<DataSourceIdentity> dataSourceIdentities ) {
        if ( dataSourceIdentities == null || dataSourceIdentities.isEmpty() ) {
            return;
        }
        Query queryPersisted = queryService.submit( query );
        s().getNamedQuery( QuerySelectionHelper.REMOVE_DATASOURCES_FOR_QUERY ).setEntity( "query", queryPersisted ).setParameterList( "dataSources", dataSourceIdentities ).executeUpdate();
        refreshRoulette( query );
    }

    public QuerySelectionHelper getQueryForDataSource( final ReadQuery q, final DataSourceIdentity dataSourceIdentity ) {
        return (QuerySelectionHelper) s().createCriteria( QuerySelectionHelper.class )
         .add( Restrictions.eq( "query", q ) ).add( Restrictions.eq( "dataSourceIdentity", dataSourceIdentity ) ).uniqueResult();
    }

    protected QuerySelectionHelper initNew( QuerySelectionHelper querySelectionHelper, ReadQuery q, DataSourceIdentity dataSourceIdentity ) {
        querySelectionHelper.setExecutions( 0 );
        querySelectionHelper.setAverageExecutionTimeNano( -1 );
        querySelectionHelper.setDataSourceIdentity( dataSourceIdentity );
        querySelectionHelper.setQuery( q );
        return querySelectionHelper;

    }

    public QuerySelectionHelper submitNewDataSourceForQuery( final ReadQuery q, final DataSourceIdentity dataSourceIdentity ) {
        ReadQuery persistedQuery = queryService.submit( q );
        QuerySelectionHelper querySelectionHelper = getQueryForDataSource( persistedQuery, dataSourceIdentity );
        if ( querySelectionHelper == null ) {
            querySelectionHelper = new QuerySelectionHelper();
            querySelectionHelper = initNew( querySelectionHelper, persistedQuery, dataSourceIdentity );
            create( querySelectionHelper );
        }
        else {
            querySelectionHelper = initNew( querySelectionHelper, persistedQuery, dataSourceIdentity );
            update( querySelectionHelper );
        }
        refreshRoulette( persistedQuery );
        sourceMultiValueMap.put( persistedQuery.getHash(), querySelectionHelper );
        return querySelectionHelper;
    }

    public Collection<QuerySelectionHelper> submit( final ReadQuery q, final DataSourceIdentity dataSourceIdentity, long executionTimeNano ) {
        ReadQuery persistedQuery = queryService.submit( q );

        Collection<QuerySelectionHelper> querySelectionHelperCollection = sourceMultiValueMap.get( persistedQuery.getHash() );
        Collection<QuerySelectionHelper> filtered = Collections2.filter( querySelectionHelperCollection, new PredicateQueryDataSource( dataSourceIdentity ) );
        if ( filtered.isEmpty() ) {
            QuerySelectionHelper querySelectionHelper = getQueryForDataSource( persistedQuery, dataSourceIdentity );
            if ( querySelectionHelper == null ) {
                querySelectionHelper = new QuerySelectionHelper();
                querySelectionHelper.setExecutions( 1 );
                querySelectionHelper.setAverageExecutionTimeNano( executionTimeNano );
                querySelectionHelper.setDataSourceIdentity( dataSourceIdentity );
                querySelectionHelper.setQuery( persistedQuery );
                querySelectionHelper = create( querySelectionHelper );
            }
            refreshRoulette( persistedQuery );
            filtered.add( querySelectionHelper );
            sourceMultiValueMap.put( persistedQuery.getHash(), querySelectionHelper );
        }
        else {
            for ( QuerySelectionHelper querySelectionHelper : filtered ) {
                long executions = querySelectionHelper.getExecutions();
                float averageExecution = querySelectionHelper.getAverageExecutionTimeNano();
                averageExecution = ( averageExecution * executions + executionTimeNano ) / ( executions + 1 );
                querySelectionHelper.setExecutions( executions + 1 );
                querySelectionHelper.setAverageExecutionTimeNano( averageExecution );
                update( querySelectionHelper );
                refreshRoulette( persistedQuery );
            }
        }
        return filtered;
    }


}
