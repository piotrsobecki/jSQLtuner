package pl.piotrsukiennik.tuner.service.impl.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.piotrsukiennik.tuner.model.other.ColumnValue;
import pl.piotrsukiennik.tuner.model.query.InsertQuery;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.model.query.impl.*;
import pl.piotrsukiennik.tuner.model.schema.Table;
import pl.piotrsukiennik.tuner.service.QueryInvalidatorService;
import pl.piotrsukiennik.tuner.service.impl.cache.SupportingHashesHolder;

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
class QueryInvalidatorServiceImpl implements QueryInvalidatorService {

    @Autowired
    private SupportingHashesHolder<ReadQuery> supportingHashesHolder;

    @Override
    public void putCachedQuery( ReadQuery query ) {
        supportingHashesHolder.putQuery( query );
    }

    @Override
    public Collection<ReadQuery> invalidates( InsertQuery insertQuery ) {
        return supportingHashesHolder.getQueriesInvalidatedBy( insertQuery.getTable() );
    }

    @Override
    public Collection<ReadQuery> invalidates( DeleteQuery deleteQuery ) {
        return supportingHashesHolder.getQueriesInvalidatedBy( deleteQuery.getTableSource().getTable() );
    }

    @Override
    public Collection<ReadQuery> invalidates( UpdateQuery updateQuery ) {
        Collection<ReadQuery> queriesToInvalidate = new LinkedHashSet<ReadQuery>();
        if ( updateQuery.getColumnValues() != null ) {
            Set<Table> tablesToStarInvalidate = new LinkedHashSet<Table>();
            for ( ColumnValue columnValue : updateQuery.getColumnValues() ) {
                tablesToStarInvalidate.add( columnValue.getColumn().getTable() );
                queriesToInvalidate.addAll( supportingHashesHolder.getQueriesInvalidatedBy( columnValue.getColumn() ) );
            }
            for ( Table table : tablesToStarInvalidate ) {
                queriesToInvalidate.addAll( supportingHashesHolder.getQueriesInvalidatedByStar( table ) );
            }
            return queriesToInvalidate;
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public Collection<ReadQuery> invalidates( AlterTableQuery alterTableQuery ) {
        return supportingHashesHolder.getQueriesInvalidatedBy( alterTableQuery.getTable() );
    }

    @Override
    public Collection<ReadQuery> invalidates( CreateTableQuery createTableQuery ) {
        return supportingHashesHolder.getQueriesInvalidatedBy( createTableQuery.getTable() );
    }

    @Override
    public Collection<ReadQuery> invalidates( TruncateQuery truncateQuery ) {
        return supportingHashesHolder.getQueriesInvalidatedBy( truncateQuery.getTable() );
    }

    @Override
    public Collection<ReadQuery> invalidates( DropTableQuery dropTableQuery ) {
        return supportingHashesHolder.getQueriesInvalidatedBy( dropTableQuery.getTable() );
    }

    @Override
    public Collection<ReadQuery> invalidates( SelectQuery selectQuery ) {
        return Collections.EMPTY_LIST;
    }


    @Override
    public Collection<ReadQuery> invalidates( CreateViewQuery createViewQuery ) {
        return Collections.EMPTY_LIST;
    }

    @Override
    public Collection<ReadQuery> invalidates( CreateIndexQuery createIndexQuery ) {
        return Collections.EMPTY_LIST;
    }

    @Override
    public Collection<ReadQuery> invalidates( CallQuery callQuery ) {
        return Collections.EMPTY_LIST;
    }

    @Override
    public Collection<ReadQuery> invalidates( GenericDropQuery genericDropQuery ) {
        return Collections.EMPTY_LIST;
    }

    @Override
    public Collection<ReadQuery> invalidates( ReplaceQuery replaceQuery ) {
        return Collections.EMPTY_LIST;
    }

}
