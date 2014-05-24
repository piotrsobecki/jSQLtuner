package pl.piotrsukiennik.tuner.model;

/**
 * @author Piotr Sukiennik
 * @date 24.05.14
 */
public interface ReadQueryExecutionComplexityEstimation extends ExecutionComplexityEstimation {

    public enum Type {
        EXECUTION_COMPLEXITY {
            @Override
            public double retrieve( ReadQueryExecutionComplexityEstimation readQueryExecutionComplexityEstimation ) {
                return readQueryExecutionComplexityEstimation.getExecutionComplexity();
            }
        };
        public abstract double retrieve(ReadQueryExecutionComplexityEstimation readQueryExecutionComplexityEstimation );
    }

    long getRowSize();

    long getRows();
}
