package pl.piotrsukiennik.tuner.persistance.model.query.execution;

import pl.piotrsukiennik.tuner.persistance.model.ValueEntity;
import pl.piotrsukiennik.tuner.persistance.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.persistance.model.query.SelectQuery;

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
        query = "CALL updateQueryExecutionSettings(:queryId)"
)
})
@NamedQueries({
        @NamedQuery(name = QueryForDataSource.REMOVE_DATASOURCE_FOR_QUERY,query = "DELETE FROM QueryForDataSource q WHERE q.query.hash = (:queryHash) AND q.dataSource = (:dataSource)"),
    @NamedQuery(name = QueryForDataSource.GET_DATASOURCE_FOR_QUERY,query = "SELECT q.dataSource FROM QueryForDataSource q WHERE q.query.hash = (:queryHash) AND (:random) BETWEEN q.rouletteShareFrom AND q.rouletteShareTo"),
   @NamedQuery(name = QueryForDataSource.GET_FOR_QUERY,query = "FROM QueryForDataSource q WHERE q.query.hash = (:queryHash) AND (:random) BETWEEN q.rouletteShareFrom AND q.rouletteShareTo")
})
public class QueryForDataSource extends ValueEntity {

    public static final String  REMOVE_DATASOURCE_FOR_QUERY = "removeDataSourceForQuery";
    public static final String  GET_DATASOURCE_FOR_QUERY = "getDataSourceForQuery";
    public static final String  GET_FOR_QUERY = "getForQuery";

    public static final String  UPDATE_QUERY_EXECUTION_SETTINGS = "updateQueryExecutionSettings";

    private ReadQuery query;
    private DataSource dataSource;
    private float averageExecutionTimeNano;
    private long executions;
    private Float probability;
    private Float rouletteShareFrom;
    private Float rouletteShareTo;


    @ManyToOne(cascade = CascadeType.ALL)
    public ReadQuery getQuery() {
        return query;
    }

    public void setQuery(ReadQuery query) {
        this.query = query;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public float getAverageExecutionTimeNano() {
        return averageExecutionTimeNano;
    }

    public void setAverageExecutionTimeNano(float averageExecutionTimeNano) {
        this.averageExecutionTimeNano = averageExecutionTimeNano;
    }

    public long getExecutions() {
        return executions;
    }

    public void setExecutions(long executions) {
        this.executions = executions;
    }
    @Column(nullable  = true)
    public Float getRouletteShareFrom() {
        return rouletteShareFrom;
    }

    public void setRouletteShareFrom(Float rouletteShareFrom) {
        this.rouletteShareFrom = rouletteShareFrom;
    }
    @Column(nullable = true)
    public Float getRouletteShareTo() {
        return rouletteShareTo;
    }

    public void setRouletteShareTo(Float rouletteShareTo) {
        this.rouletteShareTo = rouletteShareTo;
    }
    @Column(nullable = true)
    public Float getProbability() {
        return probability;
    }

    public void setProbability(Float probability) {
        this.probability = probability;
    }



}
