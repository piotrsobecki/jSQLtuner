package pl.piotrsukiennik.tuner.dto;

/**
 * @author Piotr Sukiennik
 * @date 22.05.14
 */
public class QueryComplexityEstimation {

    public enum Type {
        EXECUTION_COMPLEXITY {
            @Override
            public double retrieve( QueryComplexityEstimation queryComplexityEstimation ) {
                return queryComplexityEstimation.executionComplexity;
            }
        };
        public abstract double retrieve(QueryComplexityEstimation queryComplexityEstimation );
    }
    public static class Builder{
        private double executionComplexity;

        public Builder withExecutionComplexity( double complexity ){
            this.executionComplexity =complexity;
            return this;
        }

        public QueryComplexityEstimation build(){
            QueryComplexityEstimation queryComplexityEstimation = new QueryComplexityEstimation();
            queryComplexityEstimation.executionComplexity = executionComplexity;
            return queryComplexityEstimation;
        }

    }

    private double executionComplexity;

    public double getExecutionComplexity() {
        return executionComplexity;
    }

    void setExecutionComplexity( double executionComplexity ) {
        this.executionComplexity = executionComplexity;
    }
}
