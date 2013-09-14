package pl.piotrsukiennik.tuner.query;

import pl.piotrsukiennik.tuner.persistance.model.query.source.TableSource;
import pl.piotrsukiennik.tuner.persistance.model.schema.*;
import pl.piotrsukiennik.tuner.persistance.service.ISchemaService;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Piotr Sukiennik
 * Date: 28.07.13
 * Time: 19:26
 */
public class QueryContextManager {
    private QueryContext queryContext;
    private ISchemaService schemaService;
    public QueryContextManager(ISchemaService schemaService) {
       this(schemaService,new QueryContext());
    }
    public QueryContextManager(ISchemaService schemaService, QueryContext queryContext) {
        this.queryContext=queryContext;
        this.schemaService=schemaService;
    }

    public Database getDatabase(String databaseName) {
        if (queryContext.getDatabase()!=null){
            return queryContext.getDatabase();
        } else {
            Database database = schemaService.getDatabase(databaseName);
            queryContext.setDatabase(database);
            return database;
        }
    }
    public Schema getSchema(String schemaName) {
        if (queryContext.getSchema()!=null){
            return queryContext.getSchema();
        } else {
            Schema schema = schemaService.getSchema(queryContext.getDatabase(),schemaName);
            queryContext.setSchema(schema);
            return schema;
        }
    }
    public Column getColumn(String tableName, String columnName) {
        String key = tableName+" "+columnName;
        Column column = queryContext.getColumns().get(key);
        if (column==null){
            column = schemaService.getColumn(queryContext.getTables().get(tableName),columnName);
            queryContext.getColumns().put(key,column);
        }
        return column;
    }
    public Table getTable(String tableName) {
        String key = tableName;
        Table table = queryContext.getTables().get(key);
        if (table==null){
            table = schemaService.getTable(queryContext.getSchema(),tableName);
            queryContext.getTables().put(key,table);
        }
        return table;

    }

    private Map<String,TableSource> tableMap = new HashMap<String, TableSource>();

    public void putTableSource(TableSource tableSource) {
        if (tableSource.getAlias()==null || tableSource.getAlias().isEmpty()){
            tableMap.put(tableSource.getTable().getValue(),tableSource);
        } else {
            tableMap.put(tableSource.getAlias(),tableSource);
        }

    }
    public TableSource getTableSource(String table){
        return tableMap.get(table);
    }
}
