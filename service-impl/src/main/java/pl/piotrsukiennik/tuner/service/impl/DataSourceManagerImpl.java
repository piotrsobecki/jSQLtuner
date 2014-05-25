package pl.piotrsukiennik.tuner.service.impl;

import com.sun.rowset.CachedRowSets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.piotrsukiennik.tuner.exception.DataRetrievalException;
import pl.piotrsukiennik.tuner.model.DataSource;
import pl.piotrsukiennik.tuner.model.ReadQueryExecutionResult;
import pl.piotrsukiennik.tuner.model.ReadQueryExecutionResultBuilder;
import pl.piotrsukiennik.tuner.model.datasource.DataSourceIdentity;
import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.service.*;

import java.sql.ResultSet;
import java.util.Collection;

/**
 * Author: Piotr Sukiennik
 * Date: 31.08.13
 * Time: 18:58
 */
@Service
public class DataSourceManagerImpl implements DataSourceManager {

    @Autowired
    private DataSourceSelectionService dataSourceSelectionService;

    @Autowired
    private ReadQueryExecutionService readQueryExecutionService;

    @Autowired
    private ReadQueryInvalidatonService<ReadQuery> invalidatorService;

    @Autowired
    private DataSourceService dataSourceService;

    @Override
    public DataSourceIdentity getDataSourceIdentity() {
        return new DataSourceIdentity( DataSourceManagerImpl.class,"DataSourceManager" );
    }

    @Override
    public ResultSet execute( DataSource defaultDataSource, ReadQuery query ) throws DataRetrievalException {
        dataSourceService.setDefaultDataSource( query, defaultDataSource );
        return execute( query );
    }

    @Override
    public ResultSet execute( ReadQuery query ) throws DataRetrievalException {
        return doExecute( query ).getResultSet();
    }


    protected  ReadQueryExecutionResult execute(DataSourceIdentity dataSourceIdentity, ReadQuery query)  throws DataRetrievalException{
        DataSource dataSource = null;
        ReadQueryExecutionResult readQueryExecutionResult = null;
        if (dataSourceIdentity == null || dataSourceIdentity.getDefaultDataSource()){
            //Get default data source
            dataSource =  dataSourceService.getDefaultDataSource( query );
            //Execute using default data source
            readQueryExecutionResult =  readQueryExecutionService.execute( dataSource, query );
        } else {
            dataSource = dataSourceService.getDataSource( dataSourceIdentity );
            try {
                //Get using selected data source identity by the selector
                readQueryExecutionResult = readQueryExecutionService.execute( dataSource,query );
            }
            catch ( DataRetrievalException dre ) {
                LoggableServiceHolder.get().log( dre );
                //If something went wrong while execution using the selecting data source - try default
                readQueryExecutionResult =  readQueryExecutionService.execute( dataSource, query );
            }
        }
        return readQueryExecutionResult;
    }


    protected ReadQueryExecutionResult doExecute( ReadQuery query ) throws DataRetrievalException {
        //Select the data source for query using dataSourceSelectionService
        DataSourceIdentity dataSourceIdentity = dataSourceSelectionService.selectDataSource( query );
        ReadQueryExecutionResult readQueryExecutionResult =  execute(dataSourceIdentity,query);
        //Propagate data if shardeable
        distribute( readQueryExecutionResult );
        //Log retrieval
        LoggableServiceHolder.get().log( readQueryExecutionResult );
        //Feedback retrieval to data source selector for learning
        dataSourceSelectionService.submitExecution( readQueryExecutionResult );
        return readQueryExecutionResult;
    }

    @Override
    public void distribute( ReadQueryExecutionResult data ) {
        dataSourceSelectionService.submitExecution( data );

        Collection<DataSourceIdentity> newIdentities = dataSourceSelectionService.getNewSupportingDataSources(data );
        if (!newIdentities.isEmpty()){
            //Clone the result set
            ReadQueryExecutionResult readQueryExecutionResult = new ReadQueryExecutionResultBuilder( data )
             .withResultSet( CachedRowSets.clone( data.getResultSet() ) )
             .build();
            distribute( newIdentities,readQueryExecutionResult );
        }

    }


    protected void distribute(  Collection<DataSourceIdentity> selectedNodes,  ReadQueryExecutionResult data ) {
        ReadQuery query = data.getQuery();
        //Clone data for dataSources
        for ( DataSourceIdentity identity : selectedNodes ) {
            DataSource dataSource = dataSourceService.getDataSource( identity );
            //Distribute data for node
            dataSource.distribute( data );
            //Schedule the selection using the node
            dataSourceSelectionService.scheduleSelection( query, identity );
        }
        //Inform invalidator service about new supported query
        invalidatorService.submitInvalidatableReadQuery( query );
    }


    @Override
    public void delete( Query query ) {
        //Get read queries that this query accept
        Collection<ReadQuery> queriesToInvalidate = invalidatorService.getQueriesInvalidatedBy( query );
        //Remove option of selection
        for ( ReadQuery selectQuery : queriesToInvalidate ) {
            dataSourceSelectionService.removeSelectionOptions( selectQuery );
        }
    }

}
