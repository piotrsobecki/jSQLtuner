package pl.piotrsukiennik.tuner.model.query;

import pl.piotrsukiennik.tuner.parser.IQuery;

/**
 * Author: Piotr Sukiennik
 * Date: 30.06.13
 * Time: 16:43
 */
public abstract class SqlQueryModel implements IQuery {
    private QueryType queryType;
    private String  table;
    private String  whereColumns;
    private String  whereColumnsValues;
    private Integer rowsQueried;
    private Long    queryExecutionTime;
    private String  queryHash;
    protected SqlQueryModel(QueryType queryType) {
        this.queryType = queryType;
    }

    public String getQueryHash() {
        return queryHash;
    }

    public void setQueryHash(String queryHash) {
        this.queryHash = queryHash;
    }

    public String getWhereColumns() {
        return whereColumns;
    }

    public void setWhereColumns(String whereColumns) {
        this.whereColumns = whereColumns;
    }

    public String getWhereColumnsValues() {
        return whereColumnsValues;
    }

    public void setWhereColumnsValues(String whereColumnsValues) {
        this.whereColumnsValues = whereColumnsValues;
    }

    public QueryType getQueryType() {
        return queryType;
    }

    void setQueryType(QueryType queryType) {
        this.queryType = queryType;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public Integer getRowsQueried() {
        return rowsQueried;
    }

    public void setRowsQueried(Integer rowsQueried) {
        this.rowsQueried = rowsQueried;
    }

    public Long getQueryExecutionTime() {
        return queryExecutionTime;
    }

    public void setQueryExecutionTime(Long queryExecutionTime) {
        this.queryExecutionTime = queryExecutionTime;
    }
}
