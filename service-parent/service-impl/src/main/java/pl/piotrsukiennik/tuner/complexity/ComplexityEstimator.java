package pl.piotrsukiennik.tuner.complexity;

import pl.piotrsukiennik.tuner.dto.ReadQueryExecutionResult;

/**
 * @author Piotr Sukiennik
 * @date 22.05.14
 */
public interface ComplexityEstimator {
    ComplexityEstimation estimate(ReadQueryExecutionResult readQueryExecutionResult );
}
