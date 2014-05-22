package pl.piotrsukiennik.tuner.complexity;

import org.springframework.stereotype.Component;
import pl.piotrsukiennik.tuner.dto.ReadQueryExecutionResult;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.model.query.impl.SelectQuery;

import static pl.piotrsukiennik.tuner.util.Collections3.*;

/**

 * @author Piotr Sukiennik
 * @date 22.05.14
 */
@Component
public class ComplexityEstimatorImpl implements ComplexityEstimator {


    @Override
    public ComplexityEstimation estimate( ReadQueryExecutionResult readQueryExecutionResult ) {
        return new ComplexityEstimation.Builder()
         .withExecutionComplexity( executionComplexity( readQueryExecutionResult ) )
         .withSqlExecutionComplexity( sqlComplexity( readQueryExecutionResult.getReadQuery() ) )
         .build();
    }

    protected double executionComplexity( ReadQueryExecutionResult readQueryExecutionResult ){
        double size = readQueryExecutionResult.getRowSize() * readQueryExecutionResult.getRows();
        size = (size==0)?1:size;
        return readQueryExecutionResult.getExecutionTimeNano() / size;
    }

    protected double sqlComplexity( ReadQuery readQuery ){
        if (readQuery instanceof SelectQuery){
            return sqlComplexity( (SelectQuery) readQuery );
        }
        return 0;
    }

    protected double sqlComplexity( SelectQuery sq ){
        int sources = size( sq.getSources() );
        int groupBy = size( sq.getGroups() );
        int distinct = sq.getDistinctFragment()?1:0;
        int joins = size( sq.getJoins() );
        int orderBy = size( sq.getOrders() );
        int projections = size(sq.getProjections());
        return sources * (1 + groupBy + distinct + joins + orderBy + projections);
    }

}
