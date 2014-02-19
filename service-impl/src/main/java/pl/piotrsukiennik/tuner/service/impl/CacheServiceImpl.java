package pl.piotrsukiennik.tuner.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.piotrsukiennik.tuner.model.expression.Expression;
import pl.piotrsukiennik.tuner.model.expression.projection.ColumnProjection;
import pl.piotrsukiennik.tuner.model.expression.projection.SourceProjection;
import pl.piotrsukiennik.tuner.model.other.ColumnValue;
import pl.piotrsukiennik.tuner.model.query.InsertQuery;
import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.model.query.impl.DeleteQuery;
import pl.piotrsukiennik.tuner.model.query.impl.SelectQuery;
import pl.piotrsukiennik.tuner.model.query.impl.UpdateQuery;
import pl.piotrsukiennik.tuner.model.schema.Column;
import pl.piotrsukiennik.tuner.model.schema.Database;
import pl.piotrsukiennik.tuner.model.schema.Schema;
import pl.piotrsukiennik.tuner.model.schema.Table;
import pl.piotrsukiennik.tuner.model.source.Source;
import pl.piotrsukiennik.tuner.model.source.SubQuerySource;
import pl.piotrsukiennik.tuner.model.source.TableSource;
import pl.piotrsukiennik.tuner.service.CacheService;
import pl.piotrsukiennik.tuner.service.cache.HashesHolder;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Author: Piotr Sukiennik
 * Date: 12.09.13
 * Time: 21:04
 */
@Service
class CacheServiceImpl implements CacheService {
    public static final String STAR_PROJECTION_COLUMN_NAME = "*";

    @Autowired
    private HashesHolder<String, SelectQuery> hashesHolder;

    @Override
    public void putCachedQuery( SelectQuery query ) {
        putCachedQuery( query, query );
    }

    protected void putCachedQuery( SelectQuery objectToParseForPath, SelectQuery objectToStore ) {
        if ( objectToParseForPath.getProjections() != null ) {
            for ( Expression projection : objectToParseForPath.getProjections() ) {
                if ( projection instanceof ColumnProjection ) {
                    hashesHolder.put( getPath( ( (ColumnProjection) projection ).getColumn() ), objectToStore );
                }
                else if ( projection instanceof SourceProjection ) {
                    Source source = ( (SourceProjection) projection ).getSource();
                    if ( source instanceof SubQuerySource ) {
                        putCachedQuery( ( (SubQuerySource) source ).getSelectQuery(), objectToStore );
                    }
                    else if ( source instanceof TableSource ) {
                        hashesHolder.put( getStarPath( ( (TableSource) source ).getTable() ), objectToStore );
                    }
                }
            }
        }
    }

    @Override
    public Collection<SelectQuery> getQueriesToInvalidate( Query query ) {
        if ( query instanceof UpdateQuery ) {
            UpdateQuery updateQuery = (UpdateQuery) query;
            Collection<SelectQuery> queriesToInvalidate = new LinkedHashSet<SelectQuery>();
            if ( updateQuery.getColumnValues() != null ) {
                Set<Table> tablesToStarInvalidate = new LinkedHashSet<Table>();
                for ( ColumnValue columnValue : updateQuery.getColumnValues() ) {
                    tablesToStarInvalidate.add( columnValue.getColumn().getTable() );
                    String[] path1 = getPath( columnValue.getColumn() );
                    queriesToInvalidate.addAll( hashesHolder.getRemove( path1 ) );
                }
                for ( Table table : tablesToStarInvalidate ) {
                    String[] path2 = getStarPath( table );
                    queriesToInvalidate.addAll( hashesHolder.getRemove( path2 ) );
                }
                return queriesToInvalidate;
            }
        }
        else if ( query instanceof InsertQuery ) {
            InsertQuery insertQuery = (InsertQuery) query;
            String[] path = getPath( insertQuery.getTable() );
            return hashesHolder.getRemove( path );
        }
        else if ( query instanceof DeleteQuery ) {
            DeleteQuery deleteQuery = (DeleteQuery) query;
            String[] path = getPath( deleteQuery.getTableSource().getTable() );
            return hashesHolder.getRemove( path );
        }
        return Collections.EMPTY_LIST;
    }

    protected String[] getPath( Table table ) {
        Schema schema = table.getSchema();
        Database database = schema.getDatabase();
        return new String[] { database.getValue(), schema.getValue(), table.getValue() };
    }

    protected String[] getPath( Column column ) {
        Table table = column.getTable();
        Schema schema = table.getSchema();
        Database database = schema.getDatabase();
        return new String[] { database.getValue(), schema.getValue(), table.getValue(), column.getValue() };
    }

    protected String[] getStarPath( Table table ) {
        Schema schema = table.getSchema();
        Database database = schema.getDatabase();
        return new String[] { database.getValue(), schema.getValue(), table.getValue(), STAR_PROJECTION_COLUMN_NAME };
    }
}
