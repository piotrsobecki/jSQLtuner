package pl.piotrsukiennik.tuner.datasources.shard;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import pl.piotrsukiennik.tuner.DataSharder;
import pl.piotrsukiennik.tuner.DataSource;
import pl.piotrsukiennik.tuner.Sharder;
import pl.piotrsukiennik.tuner.dto.DataRetrieval;
import pl.piotrsukiennik.tuner.exception.DataRetrievalException;
import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.model.query.SelectQuery;
import pl.piotrsukiennik.tuner.service.CacheManagerService;
import pl.piotrsukiennik.tuner.service.QueryDataSourceSelectorService;
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
public class SharderImpl implements Sharder {


    private static final Log LOG = LogFactory.getLog( SharderImpl.class );

    @Resource
    private
    List<DataSharder> dataSharders;

    @Resource
    private
    QueryDataSourceSelectorService queryDataSourceSelectorService;

    @Resource
    private
    CacheManagerService cacheManagerService;


    @Override
    public DataRetrieval getData( ReadQuery query ) throws DataRetrievalException {
        DataSource dataSource = queryDataSourceSelectorService.selectDataSourceForQuery( query );
        if ( dataSource != null ) {
            DataRetrieval dataRetrieval = dataSource.get( query );
            dataRetrieval.setDataSourceIdentifier( dataSource.getMetaData().getIdentifier() );
            if ( LOG.isInfoEnabled() ) {
                LOG.info( String.format( "Retreived Data for (%s) using dataSource (%s)", query.getValue(), dataRetrieval.getDataSourceIdentifier() ) );
            }
            return dataRetrieval;
        }
        else {
            throw new DataRetrievalException( "Could Not Find DataSourceIdentity for query" );
        }
    }

    @Override
    public void put( ReadQuery query, CachedRowSet data ) {
        CachedRowSet clonedData = RowSet.clone( data );
        for ( DataSharder dataSharder : dataSharders ) {
            dataSharder.put( query, clonedData );
            queryDataSourceSelectorService.submit( query, dataSharder );//TODO
        }
        if ( query instanceof SelectQuery ) {
            cacheManagerService.putCachedQuery( (SelectQuery) query );
        }
    }

    @Override
    public void delete( Query query ) {
        Collection<SelectQuery> queriesToInvalidate = cacheManagerService.getQueriesToInvalidate( query );
        for ( DataSharder dataSharder : dataSharders ) {
            if ( dataSharder instanceof KeyValueDataSource ) {
                for ( SelectQuery selectQuery : queriesToInvalidate ) {
                    dataSharder.delete( selectQuery );
                }
            }
            else {
                dataSharder.delete( query );
            }
        }
        queryDataSourceSelectorService.removeDataSourcesForQueries( queriesToInvalidate, dataSharders );
    }

}
