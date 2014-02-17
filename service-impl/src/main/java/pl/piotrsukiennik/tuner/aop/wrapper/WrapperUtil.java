package pl.piotrsukiennik.tuner.aop.wrapper;

import org.apache.commons.logging.Log;
import pl.piotrsukiennik.tuner.DataSource;
import pl.piotrsukiennik.tuner.ShardService;
import pl.piotrsukiennik.tuner.dto.DataRetrieval;
import pl.piotrsukiennik.tuner.exception.DataRetrievalException;
import pl.piotrsukiennik.tuner.exception.QueryParsingNotSupportedException;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.model.query.WriteQuery;
import pl.piotrsukiennik.tuner.model.query.WriteQueryExecution;
import pl.piotrsukiennik.tuner.persistance.Dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * @author Piotr Sukiennik
 * @date 17.02.14
 */
public class WrapperUtil {

    private static final String PARSER_ERROR_FORMAT = "Query \"%s\" is not supported by parser - proceeding default execution";

    private WrapperUtil() {

    }

    static void proceed( ShardService shardService, WriteQuery query, boolean rowsAffected ) {
        proceed( shardService, query, rowsAffected ? 1 : 0 );
    }

    static void proceed( ShardService shardService, WriteQuery query, int rowsAffected ) {
        WriteQueryExecution writeQueryExecution = new WriteQueryExecution();
        writeQueryExecution.setRowsAffected( rowsAffected );
        writeQueryExecution.setTimestamp( new Timestamp( System.currentTimeMillis() ) );
        writeQueryExecution.setQuery( query );
        Dao.getCommonDao().create( writeQueryExecution );
        if ( rowsAffected > 0 ) {
            shardService.delete( query );
        }
    }


    static void log( Log log, QueryParsingNotSupportedException exception, String query ) {
        if ( log.isErrorEnabled() ) {
            log.error( String.format( PARSER_ERROR_FORMAT, query ), exception );
        }
    }

    static ResultSet getResultSet( ShardService shardService, ReadQuery readQuery, DataSource rootDataSource ) throws SQLException {
        try {
            shardService.setRootDataForQuery( readQuery, rootDataSource );
            DataRetrieval dataRetrieval = shardService.get( readQuery );
            return dataRetrieval.getResultSet();
        }
        catch ( DataRetrievalException e ) {
            Dao.getLogDao().logException( readQuery.toString(), e );
            throw new SQLException( e );
        }
    }

}
