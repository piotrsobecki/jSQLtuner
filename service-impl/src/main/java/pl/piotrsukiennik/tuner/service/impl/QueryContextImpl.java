package pl.piotrsukiennik.tuner.service.impl;

import pl.piotrsukiennik.tuner.dto.QueryContextDto;
import pl.piotrsukiennik.tuner.model.query.expression.Expression;
import pl.piotrsukiennik.tuner.model.query.other.GroupByFragment;
import pl.piotrsukiennik.tuner.model.query.source.TableSource;
import pl.piotrsukiennik.tuner.model.schema.*;
import pl.piotrsukiennik.tuner.persistance.Dao;
import pl.piotrsukiennik.tuner.service.QueryContext;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Piotr Sukiennik
 * Date: 28.07.13
 * Time: 19:26
 */
public class QueryContextImpl implements QueryContext {
    private static final String STRIP_TOKENS = "[` !@#$%^&*();'?><_+-=]";

    private Map<String, TableSource> tableMap = new HashMap<String, TableSource>();

    protected QueryContextDto queryContextDto;

    protected TableSource lastAttachedTableSource;

    public QueryContextImpl() {
        this( new QueryContextDto() );
    }

    public QueryContextImpl( QueryContextDto queryContextDto ) {
        this.queryContextDto = queryContextDto;
    }

    protected String correctName( String input ) {
        return input.replaceAll( STRIP_TOKENS, "" ).toLowerCase();
    }

    @Override
    public Database getDatabase( String databaseName ) {
        String nameCorrected = correctName( databaseName );

        if ( queryContextDto.getDatabase() != null ) {
            return queryContextDto.getDatabase();
        }
        else {
            Database database = Dao.getSchemaDao().getDatabase( nameCorrected );
            queryContextDto.setDatabase( database );
            return database;
        }
    }

    @Override
    public Schema getSchema( String schemaName ) {
        String schemaNameCorrected = correctName( schemaName );

        if ( queryContextDto.getSchema() != null ) {
            return queryContextDto.getSchema();
        }
        else {
            Schema schema = Dao.getSchemaDao().getSchema( queryContextDto.getDatabase(), schemaNameCorrected );
            queryContextDto.setSchema( schema );
            return schema;
        }
    }

    @Override
    public Column getColumn( Table table, String columnName ) {
        String columnNameCorrected = correctName( columnName );
        String key = table.getValue() + "." + columnName;
        Column column = queryContextDto.getColumns().get( key );
        if ( column == null ) {
            column = Dao.getSchemaDao().getColumn( table, columnNameCorrected );
            queryContextDto.getColumns().put( key, column );
        }
        return column;
    }

    @Override
    public Column getColumn( String tableName, String columnName ) {
        String tableNameCorrected = correctName( tableName );
        return getColumn( getTable( tableNameCorrected ), columnName );
    }

    @Override
    public GroupByFragment getGroupByFragment( Expression element, int position ) {
        return Dao.getQueryDao().getGroupByFragment( element, position );

    }

    @Override
    public Table getTable( String tableName ) {
        String nameCorrected = correctName( tableName );
        String key = tableName;
        Table table = queryContextDto.getTables().get( key );
        if ( table == null ) {
            table = Dao.getSchemaDao().getTable( queryContextDto.getSchema(), nameCorrected );
            queryContextDto.getTables().put( key, table );
        }
        return table;

    }

    @Override
    public Index getIndex( Table table, String indexName ) {
        String nameCorrected = correctName( indexName );
        String key = nameCorrected;
        Index index = queryContextDto.getIndexes().get( key );
        if ( index == null ) {
            index = Dao.getSchemaDao().getIndex( table, nameCorrected );
            queryContextDto.getIndexes().put( key, index );
        }
        return index;

    }

    @Override
    public TableSource mergeTableSource( TableSource tableSource ) {
        String identifier = null;
        if ( tableSource.getAlias() == null || tableSource.getAlias().isEmpty() ) {
            identifier = tableSource.getTable().getValue();
        }
        else {
            identifier = tableSource.getAlias();
        }
        TableSource tableSourceMerged = tableMap.get( identifier );
        if ( tableSourceMerged == null ) {
            tableMap.put( identifier, tableSource );
            tableSourceMerged = tableSource;
        }
        lastAttachedTableSource = tableSourceMerged;
        return tableSourceMerged;
    }

    @Override
    public TableSource getTableSource( String table ) {
        return tableMap.get( table );
    }

    @Override
    public TableSource getLastAttachedTableSource() {
        return lastAttachedTableSource;
    }
}
