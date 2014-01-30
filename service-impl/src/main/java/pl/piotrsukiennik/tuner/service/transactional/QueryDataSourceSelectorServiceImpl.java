package pl.piotrsukiennik.tuner.service.transactional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.piotrsukiennik.tuner.DataSource;
import pl.piotrsukiennik.tuner.dto.DataRetrieval;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.model.query.WriteQueryExecution;
import pl.piotrsukiennik.tuner.model.query.execution.DataSourceIdentity;
import pl.piotrsukiennik.tuner.model.query.execution.QuerySelectionHelper;
import pl.piotrsukiennik.tuner.persistance.DaoHolder;
import pl.piotrsukiennik.tuner.service.LocalDataSourceService;
import pl.piotrsukiennik.tuner.service.QueryDataSourceSelectorService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Author: Piotr Sukiennik
 * Date: 01.09.13
 * Time: 12:11
 */
@Service
@Transactional(value = "jsqlTunerTransactionManager")
class QueryDataSourceSelectorServiceImpl implements QueryDataSourceSelectorService {

    @Resource
    private LocalDataSourceService dataSourceMapper;

    @Override
    public void removeDataSourcesForQueries( Collection<? extends ReadQuery> queries,
                                             Collection<? extends DataSource> dataSources ) {
        Collection<DataSourceIdentity> persistedSources = new ArrayList<DataSourceIdentity>();
        for ( DataSource dataSource : dataSources ) {
            persistedSources.add( getRemote( dataSource.getMetaData().getIdentifier() ) );
        }
        DaoHolder.getQueryExecutionDao().removeDataSourcesForQueries( queries, persistedSources );
    }

    @Override
    public void removeDataSourcesForQuery( ReadQuery query,
                                           Collection<? extends DataSource> dataSources ) {
        Collection<DataSourceIdentity> persistedSources = new ArrayList<DataSourceIdentity>();
        for ( DataSource dataSource : dataSources ) {
            persistedSources.add( getRemote( dataSource.getMetaData().getIdentifier() ) );
        }
        DaoHolder.getQueryExecutionDao().removeDataSourcesForQuery( query, persistedSources );
    }

    @Override
    public void removeDataSourceForQuery( ReadQuery query,
                                          DataSource dataSource ) {

        DaoHolder.getQueryExecutionDao().removeDataSourceForQuery( query, getRemote( dataSource.getMetaData().getIdentifier() ) );
    }

    @Override
    public QuerySelectionHelper submit( ReadQuery query,
                                        DataSource dataSource ) {

        return DaoHolder.getQueryExecutionDao().submitNewDataSourceForQuery( query, getRemote( dataSource.getMetaData().getIdentifier() ) );
    }

    @Override
    public Collection<QuerySelectionHelper> submit( ReadQuery query,
                                                    DataRetrieval dataRetrieval ) {

        return DaoHolder.getQueryExecutionDao().submit( query,
         getRemote( dataRetrieval.getDataSourceIdentifier() ),
         dataRetrieval.getExecutionTimeNano() );
    }

    protected DataSourceIdentity getRemote( String dataSourceIdentifier ) {
        return DaoHolder.getQueryExecutionDao().getDataSource( dataSourceIdentifier );
    }

    protected DataSource getLocal( ReadQuery selectQuery, DataSourceIdentity dataSourceIdentity ) {
        Collection<DataSource> collection = dataSourceMapper.getLocal( selectQuery, dataSourceIdentity );
        Iterator<DataSource> iterator = collection.iterator();
        if ( iterator.hasNext() ) {
            DataSource dataSourceLocal = iterator.next();
            dataSourceLocal.setDataSourceIdentity( dataSourceIdentity );
            return dataSourceLocal;
        }
        else {
            return null;
        }
    }

    @Override
    public DataSource selectDataSourceForQuery( ReadQuery selectQuery ) {
        // List<DataSourceIdentity> dataSources = DaoHolder.getQueryExecutionDao().getDataSourcesForQuery( selectQuery );

        // getLocal( selectQuery, dataSources.iterator().next() );
        return null;
    }

    @Override
    public void submit( WriteQueryExecution writeQueryExecution ) {
        DaoHolder.getCommonDao().create( writeQueryExecution );
    }
}
