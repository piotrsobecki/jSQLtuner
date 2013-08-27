package pl.piotrsukiennik.tuner.persistance.model.query.execution;

import pl.piotrsukiennik.tuner.persistance.model.ValueEntity;
import pl.piotrsukiennik.tuner.persistance.model.query.Query;

import javax.persistence.*;

/**
 * Author: Piotr Sukiennik
 * Date: 27.08.13
 * Time: 20:00
 */

@Entity
@NamedNativeQueries({
@NamedNativeQuery(
        name = QueryForDataSource.UPDATE_QUERY_EXECUTION_SETTINGS,
        query = "CALL updateQueryExecutionSettings(:queryId,:dataSourceId)"
)
})
public class QueryForDataSource extends ValueEntity {

    public static final String  UPDATE_QUERY_EXECUTION_SETTINGS = "updateQueryExecutionSettings";

    private Query query;
    private DataSource dataSource;
    private long averageRows;
    private long averageExecutionTimeMillis;
    private long executions;
    private float probability;
    private float rouletteShareFrom;
    private float rouletteShareTo;


    @ManyToOne(cascade = CascadeType.ALL)
    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    public long getAverageRows() {
        return averageRows;
    }

    public void setAverageRows(long averageRows) {
        this.averageRows = averageRows;
    }

    public long getAverageExecutionTimeMillis() {
        return averageExecutionTimeMillis;
    }

    public void setAverageExecutionTimeMillis(long averageExecutionTimeMillis) {
        this.averageExecutionTimeMillis = averageExecutionTimeMillis;
    }

    public long getExecutions() {
        return executions;
    }

    public void setExecutions(long executions) {
        this.executions = executions;
    }

    public float getRouletteShareFrom() {
        return rouletteShareFrom;
    }

    public void setRouletteShareFrom(float rouletteShareFrom) {
        this.rouletteShareFrom = rouletteShareFrom;
    }

    public float getRouletteShareTo() {
        return rouletteShareTo;
    }

    public void setRouletteShareTo(float rouletteShareTo) {
        this.rouletteShareTo = rouletteShareTo;
    }

    public float getProbability() {
        return probability;
    }

    public void setProbability(float probability) {
        this.probability = probability;
    }
}
