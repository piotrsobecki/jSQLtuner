package pl.piotrsukiennik.tuner.complexity;

import org.springframework.stereotype.Component;
import pl.piotrsukiennik.tuner.dto.QueryComplexityEstimation;
import pl.piotrsukiennik.tuner.dto.ReadQueryExecutionResult;

/**

 * @author Piotr Sukiennik
 * @date 22.05.14
 */
@Component
public class ComplexityEstimatorImpl implements ComplexityEstimator {


    @Override
    public QueryComplexityEstimation estimate( ReadQueryExecutionResult readQueryExecutionResult ) {
        return new QueryComplexityEstimation.Builder()
         .withExecutionComplexity( executionComplexity( readQueryExecutionResult ) )
         .build();
    }

    protected double executionComplexity( ReadQueryExecutionResult readQueryExecutionResult ){
        double size = readQueryExecutionResult.getRowSize() * readQueryExecutionResult.getRows();
        size = (size==0)?1:size;
        return readQueryExecutionResult.getExecutionTimeNano() / size;
    }


}
