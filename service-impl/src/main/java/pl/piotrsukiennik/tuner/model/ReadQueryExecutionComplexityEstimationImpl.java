package pl.piotrsukiennik.tuner.model;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * @author Piotr Sukiennik
 * @date 22.05.14
 */
public class ReadQueryExecutionComplexityEstimationImpl implements ExecutionComplexityEstimation, ReadQueryExecutionComplexityEstimation {

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
            ReadQueryExecutionComplexityEstimationImpl readQueryExecutionComplexityEstimationImpl = new ReadQueryExecutionComplexityEstimationImpl();
            readQueryExecutionComplexityEstimationImpl.executionComplexity = executionComplexity;
            readQueryExecutionComplexityEstimationImpl.rows=rows;
            readQueryExecutionComplexityEstimationImpl.rowSize=rowSize;
            readQueryExecutionComplexityEstimationImpl.executionTimeNano=executionTimeNano;
            return readQueryExecutionComplexityEstimationImpl;
        }

    }

    private double executionComplexity;

    private long rowSize;
    private long rows;
    private long executionTimeNano;


    @Override
    public double getExecutionComplexity() {
        return executionComplexity;
    }

    @Override
    public long getRowSize() {
        return rowSize;
    }

    @Override
    public long getRows() {
        return rows;
    }

    public long getExecutionTimeNano() {
        return executionTimeNano;
    }

    @Override
    public String toString() {
        return new ReflectionToStringBuilder( this ).toString();
    }
}
