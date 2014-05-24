package pl.piotrsukiennik.tuner.service.impl;

import org.springframework.stereotype.Component;
import pl.piotrsukiennik.tuner.dto.ReadQueryExecutionComplexityEstimation;
import pl.piotrsukiennik.tuner.exception.ResultSetMetaDataRetrievalException;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.service.QueryExecutionComplexityService;
import pl.piotrsukiennik.tuner.service.RowSetSizeService;

import javax.annotation.Resource;
import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;

/**

 * @author Piotr Sukiennik
 * @date 22.05.14
 */
@Component
public class QueryExecutionComplexityServiceImpl implements QueryExecutionComplexityService {

    @Resource
    private RowSetSizeService rowSetSizeService;


    @Override
    public ReadQueryExecutionComplexityEstimation estimate( ReadQuery readQuery, CachedRowSet cachedRowSet, long executionTimeNano ) throws ResultSetMetaDataRetrievalException{
        try {
            long rowSize = rowSetSizeService.sizeof( cachedRowSet );
            long rows = cachedRowSet.size();
            double complexity = executionComplexity( rowSize,rows,executionTimeNano );
            return  new ReadQueryExecutionComplexityEstimation.Builder()
             .withExecutionTimeNano( executionTimeNano )
             .withRows( rows )
             .withRowSize( rowSize )
             .withExecutionComplexity( complexity )
             .build();
        }
        catch ( SQLException e ) {
            throw new ResultSetMetaDataRetrievalException( "Could not get meta data for query",e,readQuery );
        }
    }

    protected double executionComplexity( long rowSize, long rows, long executionTimeNano ){
        double size =rowSize * rows;
        size = (size==0)?1:size;
        return executionTimeNano / size;
    }
}
