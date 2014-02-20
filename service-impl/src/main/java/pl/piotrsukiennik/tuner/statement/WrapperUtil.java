package pl.piotrsukiennik.tuner.statement;

import pl.piotrsukiennik.tuner.DataSource;
import pl.piotrsukiennik.tuner.ShardService;
import pl.piotrsukiennik.tuner.dto.DataRetrieval;
import pl.piotrsukiennik.tuner.exception.DataRetrievalException;
import pl.piotrsukiennik.tuner.model.log.WriteQueryExecution;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.model.query.WriteQuery;
import pl.piotrsukiennik.tuner.persistance.Dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * @author Piotr Sukiennik
 * @date 17.02.14
 */
public class WrapperUtil {

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
        Dao.getCommon().create( writeQueryExecution );
        if ( rowsAffected > 0 ) {
            shardService.delete( query );
        }
    }


    static ResultSet getResultSet( ShardService shardService, ReadQuery readQuery, DataSource rootDataSource ) throws SQLException {
        try {
            shardService.setRootDataForQuery( readQuery, rootDataSource );
            DataRetrieval dataRetrieval = shardService.get( readQuery );
            return dataRetrieval.getResultSet();
        }
        catch ( DataRetrievalException e ) {
            Dao.getLog().log( readQuery.toString(), e );
            throw new SQLException( e );
        }
    }

}
