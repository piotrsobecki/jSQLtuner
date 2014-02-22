package pl.piotrsukiennik.tuner.service.impl;

import pl.piotrsukiennik.tuner.dto.QueryElementsCache;
import pl.piotrsukiennik.tuner.model.expression.Expression;
import pl.piotrsukiennik.tuner.model.other.GroupByFragment;
import pl.piotrsukiennik.tuner.model.schema.*;
import pl.piotrsukiennik.tuner.model.source.TableSource;
import pl.piotrsukiennik.tuner.persistance.Dao;
import pl.piotrsukiennik.tuner.service.QueryElements;

/**
 * Author: Piotr Sukiennik
 * Date: 28.07.13
 * Time: 19:26
 */
public class QueryElementsImpl implements QueryElements {
    private static final String STRIP_TOKENS = "[` !@#$%^&*();'?><_+-=]";


    protected QueryElementsCache queryElementsCache;

    protected TableSource lastAttachedTableSource;

    private Database database;

    private Schema schema;

    public QueryElementsImpl( String database, String schema ) {
        this( new QueryElementsCache() );
        this.database = this.getDatabase( database );
        this.schema = this.getSchema( schema );
    }

    public QueryElementsImpl( QueryElementsCache queryElementsCache ) {
        this.queryElementsCache = queryElementsCache;
    }

    protected String correctName( String input ) {
        return input.replaceAll( STRIP_TOKENS, "" ).toLowerCase();
    }

    @Override
    public Database getDatabase( String databaseName ) {
        String nameCorrected = correctName( databaseName );
        if ( queryElementsCache.getDatabase() != null ) {
            return queryElementsCache.getDatabase();
        }
        else {
            Database database = Dao.getSchema().getDatabase( nameCorrected );
            queryElementsCache.setDatabase( database );
            return database;
        }
    }

    @Override
    public Schema getSchema( String schemaName ) {
        String schemaNameCorrected = correctName( schemaName );
        if ( queryElementsCache.getSchema() != null ) {
            return queryElementsCache.getSchema();
        }
        else {
            Schema schema = Dao.getSchema().getSchema( database, schemaNameCorrected );
            queryElementsCache.setSchema( schema );
            return schema;
        }
    }

    @Override
    public Column getColumn( Table table, String columnName ) {
        String columnNameCorrected = correctName( columnName );
        String key = table.getValue() + "." + columnName;
        Column column = queryElementsCache.getColumns().get( key );
        if ( column == null ) {
            column = Dao.getSchema().getColumn( table, columnNameCorrected );
            queryElementsCache.getColumns().put( key, column );
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
        return Dao.getQuery().getGroupByFragment( element, position );

    }

    @Override
    public Table getTable( String tableName ) {
        String nameCorrected = correctName( tableName );
        String key = tableName;
        Table table = queryElementsCache.getTables().get( key );
        if ( table == null ) {
            table = Dao.getSchema().getTable( schema, nameCorrected );
            queryElementsCache.getTables().put( key, table );
        }
        return table;

    }

    @Override
    public Index getIndex( Table table, String indexName ) {
        String nameCorrected = correctName( indexName );
        String key = table.getValue() + "." + nameCorrected;
        Index index = queryElementsCache.getIndexes().get( key );
        if ( index == null ) {
            index = Dao.getSchema().getIndex( table, nameCorrected );
            queryElementsCache.getIndexes().put( key, index );
        }
        return index;

    }

}
