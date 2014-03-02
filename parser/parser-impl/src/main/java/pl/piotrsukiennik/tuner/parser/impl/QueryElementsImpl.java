package pl.piotrsukiennik.tuner.parser.impl;

import pl.piotrsukiennik.tuner.dto.QueryElementsCache;
import pl.piotrsukiennik.tuner.model.expression.Expression;
import pl.piotrsukiennik.tuner.model.expression.projection.ColumnProjection;
import pl.piotrsukiennik.tuner.model.expression.projection.StarProjection;
import pl.piotrsukiennik.tuner.model.other.GroupByFragment;
import pl.piotrsukiennik.tuner.model.other.JoinFragment;
import pl.piotrsukiennik.tuner.model.other.OrderByFragment;
import pl.piotrsukiennik.tuner.model.schema.*;
import pl.piotrsukiennik.tuner.model.source.TableSource;
import pl.piotrsukiennik.tuner.parser.QueryElements;
import pl.piotrsukiennik.tuner.service.QueryElementService;
import pl.piotrsukiennik.tuner.service.SchemaService;

/**
 * Author: Piotr Sukiennik
 * Date: 28.07.13
 * Time: 19:26
 */
public class QueryElementsImpl implements QueryElements {
    private static final String STRIP_TOKENS = "[` !@#$%^&*();'?><_+-=]";

    protected QueryElementsCache queryElementsCache;

    protected SchemaService schemaService;

    protected QueryElementService elementService;

    private Database database;

    private Schema schema;

    public QueryElementsImpl( SchemaService schemaService, QueryElementService elementService, String database, String schema ) {
        this( schemaService, elementService, new QueryElementsCache() );
        this.database = this.getDatabase( database );
        this.schema = this.getSchema( schema );
    }

    public QueryElementsImpl( SchemaService schemaService, QueryElementService elementService, QueryElementsCache queryElementsCache ) {
        this.queryElementsCache = queryElementsCache;
        this.schemaService = schemaService;
        this.elementService = elementService;
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
            Database database = schemaService.getDatabase( nameCorrected );
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
            Schema schema = schemaService.getSchema( database, schemaNameCorrected );
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
            column = schemaService.getColumn( table, columnNameCorrected );
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
        return elementService.getOrCreate( element, position );
    }

    @Override
    public OrderByFragment getOrderByFragment( OrderByFragment orderByFragment ) {
        return elementService.getOrCreate( orderByFragment );
    }

    @Override
    public StarProjection getColumnProjection( StarProjection starProjection ) {
        return elementService.getOrCreate( starProjection );
    }

    @Override
    public Table getTable( String tableName ) {
        String nameCorrected = correctName( tableName );
        String key = tableName;
        Table table = queryElementsCache.getTables().get( key );
        if ( table == null ) {
            table = schemaService.getTable( schema, nameCorrected );
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
            index = schemaService.getIndex( table, nameCorrected );
            queryElementsCache.getIndexes().put( key, index );
        }
        return index;

    }

    @Override
    public ColumnProjection getColumnProjection( ColumnProjection projection ) {
        return elementService.getOrCreate( projection );
    }

    @Override
    public JoinFragment getJoinFragment( JoinFragment joinFragment ) {
        return elementService.getOrCreate( joinFragment );
    }

    @Override
    public TableSource getTableSource( TableSource tableSource ) {
        return elementService.getOrCreate( tableSource );
    }

    @Override
    public <E extends Expression> E getExpression( E expression ) {
        return elementService.getOrCreate( expression );
    }
}
