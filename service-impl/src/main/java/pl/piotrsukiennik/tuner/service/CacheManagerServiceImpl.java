package pl.piotrsukiennik.tuner.service;

import org.springframework.stereotype.Service;
import pl.piotrsukiennik.tuner.model.query.*;
import pl.piotrsukiennik.tuner.model.query.other.ColumnValue;
import pl.piotrsukiennik.tuner.model.query.projection.ColumnProjection;
import pl.piotrsukiennik.tuner.model.query.projection.Projection;
import pl.piotrsukiennik.tuner.model.query.projection.SourceProjection;
import pl.piotrsukiennik.tuner.model.query.source.Source;
import pl.piotrsukiennik.tuner.model.query.source.SubQuerySource;
import pl.piotrsukiennik.tuner.model.query.source.TableSource;
import pl.piotrsukiennik.tuner.model.schema.Column;
import pl.piotrsukiennik.tuner.model.schema.Database;
import pl.piotrsukiennik.tuner.model.schema.Schema;
import pl.piotrsukiennik.tuner.model.schema.Table;
import pl.piotrsukiennik.tuner.service.cache.model.HashesHolderManager;
import pl.piotrsukiennik.tuner.service.cache.model.build.EnterableHolderFactory;

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
class CacheManagerServiceImpl implements CacheManagerService {

    private HashesHolderManager<String, SelectQuery> hashesHolderManager;

    public CacheManagerServiceImpl() {
        EnterableHolderFactory holderFactory = new EnterableHolderFactory( LinkedHashSet.class );
        hashesHolderManager = new HashesHolderManager<String, SelectQuery>( holderFactory );
    }

    @Override
    public void putCachedQuery( SelectQuery query ) {
        putCachedQuery( query, query );
    }

    protected void putCachedQuery( SelectQuery objectToParseForPath, SelectQuery objectToStore ) {
        if ( objectToParseForPath.getProjections() != null ) {
            for ( Projection projection : objectToParseForPath.getProjections() ) {
                if ( projection instanceof ColumnProjection ) {
                    hashesHolderManager.put( getPath( ( (ColumnProjection) projection ).getColumn() ), objectToStore );
                }
                else if ( projection instanceof SourceProjection ) {
                    Source source = ( (SourceProjection) projection ).getSource();
                    if ( source instanceof SubQuerySource ) {
                        putCachedQuery( ( (SubQuerySource) source ).getSelectQuery(), objectToStore );
                    }
                    else if ( source instanceof TableSource ) {
                        hashesHolderManager.put( getStarPath( ( (TableSource) source ).getTable() ), objectToStore );
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
                    queriesToInvalidate.addAll( hashesHolderManager.getRemove( path1 ) );
                }
                for ( Table table : tablesToStarInvalidate ) {
                    String[] path2 = getStarPath( table );
                    queriesToInvalidate.addAll( hashesHolderManager.getRemove( path2 ) );
                }
                return queriesToInvalidate;
            }
        }
        else if ( query instanceof InsertQuery ) {
            InsertQuery insertQuery = ( (InsertQuery) query );
            String[] path = getPath( insertQuery.getTable() );
            return hashesHolderManager.getRemove( path );
        }
        else if ( query instanceof DeleteQuery ) {
            DeleteQuery deleteQuery = ( (DeleteQuery) query );
            String[] path = getPath( deleteQuery.getTableSource().getTable() );
            return hashesHolderManager.getRemove( path );
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
