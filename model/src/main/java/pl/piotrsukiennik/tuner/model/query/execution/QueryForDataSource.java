package pl.piotrsukiennik.tuner.model.query.execution;

import pl.piotrsukiennik.tuner.model.ValueEntity;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;

import javax.persistence.*;

/**
 * Author: Piotr Sukiennik
 * Date: 27.08.13
 * Time: 20:00
 */

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NamedNativeQueries({
 @NamedNativeQuery(
  name = QueryForDataSource.UPDATE_QUERY_EXECUTION_SETTINGS,
  query = "CALL updateQueryExecutionSettings(:queryId)"
 )
})
@NamedQueries({
 @NamedQuery(name = QueryForDataSource.REMOVE_DATASOURCES_FOR_QUERIES, query = "DELETE FROM QueryForDataSource q WHERE q.query in (:queries) AND q.dataSource in (:dataSources)"),
 @NamedQuery(name = QueryForDataSource.REMOVE_DATASOURCES_FOR_QUERY, query = "DELETE FROM QueryForDataSource q WHERE q.query = (:query) AND q.dataSource in (:dataSources)"),
 @NamedQuery(name = QueryForDataSource.REMOVE_DATASOURCE_FOR_QUERY, query = "DELETE FROM QueryForDataSource q WHERE q.query = (:query) AND q.dataSource = (:dataSource)"),
 @NamedQuery(name = QueryForDataSource.GET_DATASOURCE_FOR_QUERY, query = "SELECT q.dataSource FROM QueryForDataSource q WHERE q.query.hash = (:queryHash) AND (:random) BETWEEN q.rouletteShareFrom AND q.rouletteShareTo"),
 @NamedQuery(name = QueryForDataSource.GET_FOR_QUERY, query = "FROM QueryForDataSource q WHERE q.query.hash = (:queryHash) AND (:random) BETWEEN q.rouletteShareFrom AND q.rouletteShareTo")
})
public class QueryForDataSource extends ValueEntity {

    public static final String REMOVE_DATASOURCES_FOR_QUERIES = "removeDataSourcesForQueries";

    public static final String REMOVE_DATASOURCES_FOR_QUERY = "removeDataSourcesForQuery";

    public static final String REMOVE_DATASOURCE_FOR_QUERY = "removeDataSourceForQuery";

    public static final String GET_DATASOURCE_FOR_QUERY = "getDataSourceForQuery";

    public static final String GET_FOR_QUERY = "getForQuery";

    public static final String UPDATE_QUERY_EXECUTION_SETTINGS = "updateQueryExecutionSettings";

    private ReadQuery query;

    private DataSource dataSource;

    private float averageExecutionTimeNano;

    private long executions;

    private Float probability;

    private Float rouletteShareFrom;

    private Float rouletteShareTo;


    @ManyToOne(cascade = CascadeType.MERGE)
    public ReadQuery getQuery() {
        return query;
    }

    public void setQuery( ReadQuery query ) {
        this.query = query;
    }

    @ManyToOne(cascade = CascadeType.MERGE)
    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource( DataSource dataSource ) {
        this.dataSource = dataSource;
    }

    public float getAverageExecutionTimeNano() {
        return averageExecutionTimeNano;
    }

    public void setAverageExecutionTimeNano( float averageExecutionTimeNano ) {
        this.averageExecutionTimeNano = averageExecutionTimeNano;
    }

    public long getExecutions() {
        return executions;
    }

    public void setExecutions( long executions ) {
        this.executions = executions;
    }

    @Column(nullable = true)
    public Float getRouletteShareFrom() {
        return rouletteShareFrom;
    }

    public void setRouletteShareFrom( Float rouletteShareFrom ) {
        this.rouletteShareFrom = rouletteShareFrom;
    }

    @Column(nullable = true)
    public Float getRouletteShareTo() {
        return rouletteShareTo;
    }

    public void setRouletteShareTo( Float rouletteShareTo ) {
        this.rouletteShareTo = rouletteShareTo;
    }

    @Column(nullable = true)
    public Float getProbability() {
        return probability;
    }

    public void setProbability( Float probability ) {
        this.probability = probability;
    }


}
