package pl.piotrsukiennik.tuner.service.transactional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.piotrsukiennik.tuner.IDataSource;
import pl.piotrsukiennik.tuner.dto.DataRetrieval;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.model.query.execution.DataSource;
import pl.piotrsukiennik.tuner.model.query.execution.QueryForDataSource;
import pl.piotrsukiennik.tuner.persistance.DaoHolder;
import pl.piotrsukiennik.tuner.service.LocalDataSourceService;
import pl.piotrsukiennik.tuner.service.QueryExecutionService;

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
class QueryExecutionServiceImpl implements QueryExecutionService {

    @Resource
    private LocalDataSourceService dataSourceMapper;

    @Override
    public void removePossibleDataSources( Collection<? extends ReadQuery> queries,
                                           Collection<? extends IDataSource> dataSources ) {
        Collection<DataSource> persistedSources = new ArrayList<DataSource>();
        for ( IDataSource dataSource : dataSources ) {
            persistedSources.add( getRemote( dataSource.getMetaData().getIdentifier() ) );
        }
        DaoHolder.getQueryExecutionDao().removeDataSourcesForQueries( queries, persistedSources );
    }

    @Override
    public void removePossibleDataSources( ReadQuery query,
                                           Collection<? extends IDataSource> dataSources ) {
        Collection<DataSource> persistedSources = new ArrayList<DataSource>();
        for ( IDataSource dataSource : dataSources ) {
            persistedSources.add( getRemote( dataSource.getMetaData().getIdentifier() ) );
        }
        DaoHolder.getQueryExecutionDao().removeDataSourcesForQuery( query, persistedSources );
    }

    @Override
    public void removePossibleDataSource( ReadQuery query,
                                          IDataSource dataSource ) {

        DaoHolder.getQueryExecutionDao().removeDataSourceForQuery( query, getRemote( dataSource.getMetaData().getIdentifier() ) );
    }

    @Override
    public QueryForDataSource submitPossibleDataSource( ReadQuery query,
                                                        IDataSource dataSource ) {

        return DaoHolder.getQueryExecutionDao().submitNewDataSourceForQuery( query, getRemote( dataSource.getMetaData().getIdentifier() ) );
    }

    @Override
    public Collection<QueryForDataSource> submit( ReadQuery query,
                                                  DataRetrieval dataRetrieval ) {

        return DaoHolder.getQueryExecutionDao().submit( query,
         getRemote( dataRetrieval.getDataSourceIdentifier() ),
         dataRetrieval.getExecutionTimeNano() );
    }

    protected DataSource getRemote( String dataSourceIdentifier ) {
        return DaoHolder.getQueryExecutionDao().getDataSource( dataSourceIdentifier );
    }

    protected IDataSource getLocal( ReadQuery selectQuery, DataSource dataSource ) {
        Collection<IDataSource> collection = dataSourceMapper.getLocal( selectQuery, dataSource );
        Iterator<IDataSource> iterator = collection.iterator();
        if ( iterator.hasNext() ) {
            IDataSource dataSourceLocal = iterator.next();
            dataSourceLocal.setPersistedDataSource( dataSource );
            return dataSourceLocal;
        }
        else {
            return null;
        }
    }

    @Override
    public IDataSource getDataSourceForQuery( ReadQuery selectQuery ) {
        return getLocal( selectQuery, DaoHolder.getQueryExecutionDao().getDataSourceForQuery( selectQuery ) );
    }

}
