package pl.piotrsukiennik.tuner.service.impl;

import pl.piotrsukiennik.tuner.exception.ResultSetMetaDataRetrievalException;
import pl.piotrsukiennik.tuner.model.ReadQueryExecutionComplexityEstimation;
import pl.piotrsukiennik.tuner.model.ReadQueryExecutionComplexityEstimationImpl;
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
public abstract class QueryExecutionComplexityServiceImpl implements QueryExecutionComplexityService {

    @Resource
    private RowSetSizeService rowSetSizeService;

    protected class ComplexityExpressionParams{
        private long rowSize;
        private long rows;
        private long executionTimeNano;

        private ComplexityExpressionParams( long rowSize, long rows, long executionTimeNano ) {
            this.rowSize = rowSize;
            this.rows = rows;
            this.executionTimeNano = executionTimeNano;
        }

        public long getRowSize() {
            return rowSize;
        }

        public long getRows() {
            return rows;
        }

        public long getExecutionTimeNano() {
            return executionTimeNano;
        }
    }

    @Override
    public ReadQueryExecutionComplexityEstimation estimate( ReadQuery readQuery, CachedRowSet cachedRowSet, long executionTimeNano ) throws ResultSetMetaDataRetrievalException{
        try {
            long rowSize = rowSetSizeService.sizeof( cachedRowSet );
            long rows = cachedRowSet.size();
            double complexity = executionComplexity( new ComplexityExpressionParams( rowSize,rows,executionTimeNano ));
            return  new ReadQueryExecutionComplexityEstimationImpl.Builder()
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

    abstract double executionComplexity( ComplexityExpressionParams complexityExpressionParams );
}
