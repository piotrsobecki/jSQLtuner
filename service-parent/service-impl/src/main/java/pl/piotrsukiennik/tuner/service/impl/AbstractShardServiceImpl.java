package pl.piotrsukiennik.tuner.service.impl;

import com.google.common.base.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import pl.piotrsukiennik.tuner.DataSource;
import pl.piotrsukiennik.tuner.ShardNode;
import pl.piotrsukiennik.tuner.ShardService;
import pl.piotrsukiennik.tuner.dto.DataRetrieval;
import pl.piotrsukiennik.tuner.exception.DataRetrievalException;
import pl.piotrsukiennik.tuner.model.datasource.DataSourceIdentity;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.service.DataRetrievalService;
import pl.piotrsukiennik.tuner.service.DataSourceService;
import pl.piotrsukiennik.tuner.util.RowSet;

import javax.sql.rowset.CachedRowSet;
import java.util.Collection;
import java.util.Collections;

/**
 * @author Piotr Sukiennik
 * @date 22.05.14
 */
public abstract class AbstractShardServiceImpl implements ShardService {
    protected Collection<ShardNode> nodes;

    @Autowired
    private DataSourceService dataSourceService;

    @Autowired
    private DataRetrievalService dataRetrievalService;

    protected ShardNode getShardNode(DataSourceIdentity dataSourceIdentity){
        for (ShardNode shardNode:nodes){
            if ( Objects.equal( dataSourceIdentity, shardNode.getDataSourceIdentity() ) ){
                return shardNode;
            }
        }
        return null;
    }

    protected DataRetrieval getUsingDefault(  ReadQuery query ) throws DataRetrievalException {
        DataSource dataSource =  dataSourceService.getDataSourceDefault( query );
        return getUsingDataSource( dataSource, query );
    }

    protected DataRetrieval getUsingDataSource( DataSourceIdentity dataSourceIdentity, ReadQuery query ) throws DataRetrievalException {
        DataSource dataSource =dataSourceService.getDataSource( query, dataSourceIdentity );
        if (dataSource!=null){
            return getUsingDataSource( dataSource,query );
        }
        return null;
    }

    protected DataRetrieval getUsingDataSource( DataSource dataSource, ReadQuery query ) throws DataRetrievalException {
        DataRetrieval dataRetrieval = dataRetrievalService.get( dataSource, query );
        CachedRowSet cachedRowSet = RowSet.clone( dataRetrieval.getResultSet() );
        dataRetrieval.setResultSet( cachedRowSet );
        query.setRows( cachedRowSet.size() );
        return dataRetrieval;
    }

    @Autowired
    public void setNodes( Collection<ShardNode> nodes ) {
        this.nodes = Collections.unmodifiableCollection( nodes );
    }


    public void setDefaultDataSource( ReadQuery query, DataSource dataSource ) {
        dataSourceService.setDefault( query, dataSource );
    }
}
