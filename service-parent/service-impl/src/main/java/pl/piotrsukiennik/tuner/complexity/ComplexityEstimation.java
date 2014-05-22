package pl.piotrsukiennik.tuner.complexity;

/**
 * @author Piotr Sukiennik
 * @date 22.05.14
 */
public class ComplexityEstimation {

    public enum Type {
        EXECUTION_COMPLEXITY {
            @Override
            public double retrieve( ComplexityEstimation complexityEstimation ) {
                return complexityEstimation.executionComplexity;
            }
        },
        SQL_COMPLEXITY {
            @Override
            public double retrieve( ComplexityEstimation complexityEstimation ) {
                return complexityEstimation.sqlComplexity;
            }
        };

        public abstract double retrieve(ComplexityEstimation complexityEstimation);
    }
    static class Builder{
        private double executionComplexity;
        private double sqlComplexity;

        Builder withExecutionComplexity( double complexity ){
            this.executionComplexity =complexity;
            return this;
        }
        Builder withSqlExecutionComplexity( double sqlComplexity ){
            this.sqlComplexity =sqlComplexity;
            return this;
        }
        ComplexityEstimation build(){
            ComplexityEstimation complexityEstimation = new ComplexityEstimation();
            complexityEstimation.executionComplexity = executionComplexity;
            complexityEstimation.sqlComplexity=sqlComplexity;
            return complexityEstimation;
        }

    }

    private double executionComplexity;
    private double sqlComplexity;

    public double getSqlComplexity() {
        return sqlComplexity;
    }

    public void setSqlComplexity( double sqlComplexity ) {
        this.sqlComplexity = sqlComplexity;
    }

    public double getExecutionComplexity() {
        return executionComplexity;
    }

    public void setExecutionComplexity( double executionComplexity ) {
        this.executionComplexity = executionComplexity;
    }
}
