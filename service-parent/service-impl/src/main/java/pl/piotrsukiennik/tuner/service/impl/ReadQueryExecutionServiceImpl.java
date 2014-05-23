package pl.piotrsukiennik.tuner.service.impl;

import org.springframework.stereotype.Service;
import pl.piotrsukiennik.tuner.DataSource;
import pl.piotrsukiennik.tuner.dto.ReadQueryExecutionResult;
import pl.piotrsukiennik.tuner.dto.ReadQueryExecutionResultBuilder;
import pl.piotrsukiennik.tuner.exception.DataRetrievalException;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.service.ReadQueryExecutionService;
import pl.piotrsukiennik.tuner.size.RowSetSizeEstimator;
import pl.piotrsukiennik.tuner.util.RowSet;
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
    private RowSetSizeEstimator rowSetSizeEstimator;

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
            return RowSet.clone( resultSetCallable.call() );
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
            return new ReadQueryExecutionResultBuilder()
                 .withReadQuery( query )
                 .withDataSource( dataSource.getDataSourceIdentity() )
                 .withResultSet( cachedRowSet )
                 .withExecutionTimeNano( timedCallable.getDuration( TimeUnit.NANOSECONDS ) )
                 .withRows( cachedRowSet.size() )
                 .withRowSize( rowSetSizeEstimator.sizeof( cachedRowSet.getMetaData() ) )
                 .build();
        }
        catch ( Exception e ) {
            throw new DataRetrievalException( e, query, dataSource.getDataSourceIdentity() );
        }
    }

}
