package pl.piotrsukiennik.tuner.dto;

/**
 * @author Piotr Sukiennik
 * @date 22.05.14
 */
public class ReadQueryExecutionComplexityEstimation {

    public enum Type {
        EXECUTION_COMPLEXITY {
            @Override
            public double retrieve( ReadQueryExecutionComplexityEstimation readQueryExecutionComplexityEstimation ) {
                return readQueryExecutionComplexityEstimation.executionComplexity;
            }
        };
        public abstract double retrieve(ReadQueryExecutionComplexityEstimation readQueryExecutionComplexityEstimation );
    }
    public static class Builder{
        private double executionComplexity;

        private long rowSize;
        private long rows;
        private long executionTimeNano;

        public Builder withRowSize( long rowSize ) {
            this.rowSize = rowSize;
            return this;
        }

        public Builder withRows( long rows ) {
            this.rows = rows;
            return this;
        }

        public Builder withExecutionTimeNano( long executionTimeNano ) {
            this.executionTimeNano = executionTimeNano;
            return this;
        }

        public Builder withExecutionComplexity( double complexity ){
            this.executionComplexity =complexity;
            return this;
        }

        public ReadQueryExecutionComplexityEstimation build(){
            ReadQueryExecutionComplexityEstimation readQueryExecutionComplexityEstimation = new ReadQueryExecutionComplexityEstimation();
            readQueryExecutionComplexityEstimation.executionComplexity = executionComplexity;
            readQueryExecutionComplexityEstimation.rows=rows;
            readQueryExecutionComplexityEstimation.rowSize=rowSize;
            readQueryExecutionComplexityEstimation.executionTimeNano=executionTimeNano;
            return readQueryExecutionComplexityEstimation;
        }

    }

    private double executionComplexity;

    private long rowSize;
    private long rows;
    private long executionTimeNano;


    public double getExecutionComplexity() {
        return executionComplexity;
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
