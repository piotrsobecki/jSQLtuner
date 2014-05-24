package pl.piotrsukiennik.tuner.model;

/**
 * @author Piotr Sukiennik
 * @date 24.05.14
 */
public interface ExecutionComplexityEstimation {
    double getExecutionComplexity();
    long getExecutionTimeNano();

}
