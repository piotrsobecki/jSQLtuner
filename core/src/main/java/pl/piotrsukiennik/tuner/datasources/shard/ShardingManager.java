package pl.piotrsukiennik.tuner.datasources.shard;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import pl.piotrsukiennik.tuner.IDataSharder;
import pl.piotrsukiennik.tuner.IDataSource;
import pl.piotrsukiennik.tuner.IShardingManager;
import pl.piotrsukiennik.tuner.dto.DataRetrieval;
import pl.piotrsukiennik.tuner.exception.DataRetrievalException;
import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.model.query.SelectQuery;
import pl.piotrsukiennik.tuner.service.CacheManagerService;
import pl.piotrsukiennik.tuner.service.QueryExecutionService;
import pl.piotrsukiennik.tuner.utils.RowSet;

import javax.annotation.Resource;
import javax.sql.rowset.CachedRowSet;
import java.util.Collection;
import java.util.List;

/**
 * Author: Piotr Sukiennik
 * Date: 31.08.13
 * Time: 18:58
 */
@Component
public class ShardingManager implements IShardingManager {


    private static final Log LOG = LogFactory.getLog( ShardingManager.class );

    @Resource
    private
    List<IDataSharder> dataSharders;

    @Resource
    private
    QueryExecutionService queryExecutionService;

    @Resource
    private
    CacheManagerService cacheManagerService;


    @Override
    public DataRetrieval getData( ReadQuery query ) throws DataRetrievalException {
        IDataSource dataSource = queryExecutionService.getDataSourceForQuery( query );
        if ( dataSource != null ) {
            DataRetrieval dataRetrieval = dataSource.get( query );
            dataRetrieval.setDataSourceIdentifier( dataSource.getMetaData().getIdentifier() );
            if ( LOG.isInfoEnabled() ) {
                LOG.info( String.format( "Retreived Data for (%s) using dataSource (%s)", query.getValue(), dataRetrieval.getDataSourceIdentifier() ) );
            }
            return dataRetrieval;

        }
        else {
            throw new DataRetrievalException( "Could Not Find DataSource for query" );
        }
    }

    @Override
    public void put( ReadQuery query, CachedRowSet data ) {
        CachedRowSet clonedData = RowSet.clone( data );
        for ( IDataSharder dataSharder : dataSharders ) {
            dataSharder.put( query, clonedData );
            queryExecutionService.submitPossibleDataSource( query, dataSharder );
        }
        if ( query instanceof SelectQuery ) {
            cacheManagerService.putCachedQuery( (SelectQuery) query );
        }
    }

    @Override
    public void delete( Query query ) {
        Collection<SelectQuery> queriesToInvalidate = cacheManagerService.getQueriesToInvalidate( query );
        for ( IDataSharder dataSharder : dataSharders ) {
            if ( dataSharder instanceof KeyValueDataSharder ) {
                for ( SelectQuery selectQuery : queriesToInvalidate ) {
                    dataSharder.delete( selectQuery );
                }
            }
            else {
                dataSharder.delete( query );
            }
        }
        queryExecutionService.removePossibleDataSources( queriesToInvalidate, dataSharders );
    }

}
