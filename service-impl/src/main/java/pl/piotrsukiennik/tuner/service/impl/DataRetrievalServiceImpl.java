package pl.piotrsukiennik.tuner.service.impl;

import org.springframework.stereotype.Service;
import pl.piotrsukiennik.tuner.DataSource;
import pl.piotrsukiennik.tuner.dto.DataRetrieval;
import pl.piotrsukiennik.tuner.exception.DataRetrievalException;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.service.DataRetrievalService;
import pl.piotrsukiennik.tuner.service.util.RowSet;

import javax.sql.rowset.CachedRowSet;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Piotr Sukiennik
 * @date 17.02.14
 */
@Service
public class DataRetrievalServiceImpl implements DataRetrievalService {

    @Override
    public final DataRetrieval get( DataSource dataSource, ReadQuery query ) throws DataRetrievalException {
        long rows = 0;
        long start = System.nanoTime();
        ResultSet resultSet = dataSource.get( query );
        if ( resultSet == null ) {
            throw new DataRetrievalException( "No data found for query" );
        }
        try {
            CachedRowSet cachedRowSet = RowSet.clone( resultSet );
            rows = cachedRowSet.size();
            resultSet = cachedRowSet;
        }
        catch ( SQLException e ) {
            throw new DataRetrievalException( e );
        }
        long end = System.nanoTime();
        long executionTimeNano = end - start;

        return new DataRetrieval( query, dataSource.getDataSourceIdentity(), resultSet, executionTimeNano, rows );
    }

}
