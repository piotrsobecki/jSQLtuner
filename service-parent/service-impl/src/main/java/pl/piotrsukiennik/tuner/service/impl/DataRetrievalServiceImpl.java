package pl.piotrsukiennik.tuner.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.piotrsukiennik.tuner.DataSource;
import pl.piotrsukiennik.tuner.dto.DataRetrieval;
import pl.piotrsukiennik.tuner.exception.DataRetrievalException;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.service.DataRetrievalService;
import pl.piotrsukiennik.tuner.util.RowSet;
import pl.piotrsukiennik.tuner.util.TimedCallable;
import pl.piotrsukiennik.tuner.util.TimedCallableImpl;

import javax.sql.rowset.CachedRowSet;
import java.lang.instrument.Instrumentation;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * @author Piotr Sukiennik
 * @date 17.02.14
 */
@Service
public class DataRetrievalServiceImpl implements DataRetrievalService {


    @Override
    public final DataRetrieval get( DataSource dataSource, ReadQuery query ) throws DataRetrievalException {
        TimedCallable<CachedRowSet> timedCallable = getTimedCallable( dataSource, query );
        try {
            CachedRowSet cachedRowSet = timedCallable.call();
            long size =cachedRowSet.size();
            return new DataRetrieval(
                 query,
                 dataSource.getDataSourceIdentity(),
                 cachedRowSet,
                 timedCallable.getTime( TimeUnit.NANOSECONDS ),
                 size,
                 size
            );
        }
        catch ( Exception e ) {
            throw new DataRetrievalException( e, query, dataSource.getDataSourceIdentity() );
        }
    }

    protected TimedCallable<CachedRowSet> getTimedCallable( final DataSource dataSource, final ReadQuery query ) {
        return new TimedCallableImpl<>( new Callable<CachedRowSet>() {
            @Override
            public CachedRowSet call() throws Exception {
                ResultSet resultSet = dataSource.get( query );
                if ( resultSet != null ) {
                    try {
                        return RowSet.clone( resultSet );
                    }
                    catch ( SQLException e ) {
                        throw new DataRetrievalException( e, query, dataSource.getDataSourceIdentity() );
                    }
                }
                throw new DataRetrievalException( "No data found for query", query, dataSource.getDataSourceIdentity() );
            }
        } );
    }
}
