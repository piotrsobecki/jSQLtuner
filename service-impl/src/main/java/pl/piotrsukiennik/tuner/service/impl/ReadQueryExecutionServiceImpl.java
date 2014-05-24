package pl.piotrsukiennik.tuner.service.impl;

import com.sun.rowset.CachedRowSets;
import org.springframework.stereotype.Service;
import pl.piotrsukiennik.tuner.exception.DataRetrievalException;
import pl.piotrsukiennik.tuner.model.DataSource;
import pl.piotrsukiennik.tuner.model.ReadQueryExecutionComplexityEstimation;
import pl.piotrsukiennik.tuner.model.ReadQueryExecutionResult;
import pl.piotrsukiennik.tuner.model.ReadQueryExecutionResultBuilder;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.service.QueryExecutionComplexityService;
import pl.piotrsukiennik.tuner.service.ReadQueryExecutionService;
import pl.piotrsukiennik.tuner.util.TimedCallable;
import pl.piotrsukiennik.tuner.util.TimedCallableImpl;

import javax.annotation.Resource;
import javax.sql.rowset.CachedRowSet;
import java.sql.ResultSet;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * @author Piotr Sukiennik
 * @date 17.02.14
 */
@Service
public class ReadQueryExecutionServiceImpl implements ReadQueryExecutionService {

    @Resource
    private QueryExecutionComplexityService queryExecutionComplexityService;


    private class ObtainResultSet implements Callable<ResultSet> {

        private DataSource dataSource;
        private ReadQuery query;

        private ObtainResultSet( DataSource dataSource, ReadQuery query ) {
            this.dataSource = dataSource;
            this.query = query;
        }

        @Override
        public ResultSet call() throws Exception {
            return dataSource.execute( query );
        }
    }

    private class CloneResultSet implements Callable<CachedRowSet> {

        private Callable<ResultSet> resultSetCallable;

        private CloneResultSet( Callable<ResultSet> resultSetCallable ) {
            this.resultSetCallable = resultSetCallable;
        }

        @Override
        public CachedRowSet call() throws Exception {
            return CachedRowSets.clone( resultSetCallable.call() );
        }
    }



    @Override
    public final ReadQueryExecutionResult execute( DataSource dataSource, ReadQuery query ) throws DataRetrievalException {
        TimedCallable<CachedRowSet> timedCallable =new TimedCallableImpl<>(
             new CloneResultSet(
                  new ObtainResultSet(
                       dataSource,
                       query
                  )
             )
        );

        try {
            CachedRowSet cachedRowSet = timedCallable.call();
            long executionTimeNano = timedCallable.getDuration( TimeUnit.NANOSECONDS );
            ReadQueryExecutionComplexityEstimation executionComplexityEstimation =  queryExecutionComplexityService.estimate( query, cachedRowSet, executionTimeNano );
            return new ReadQueryExecutionResultBuilder( )
                   .withReadQuery( query )
                   .withDataSource( dataSource.getDataSourceIdentity() )
                   .withResultSet( cachedRowSet )
                   .withQueryComplexityEstimation( executionComplexityEstimation )
                   .build();

        }
        catch ( Exception e ) {
            throw new DataRetrievalException( e, query, dataSource.getDataSourceIdentity() );
        }
    }

}
