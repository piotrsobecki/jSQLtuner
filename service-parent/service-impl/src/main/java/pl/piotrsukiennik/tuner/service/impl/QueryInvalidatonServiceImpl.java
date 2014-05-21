package pl.piotrsukiennik.tuner.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.piotrsukiennik.tuner.model.other.ColumnValue;
import pl.piotrsukiennik.tuner.model.query.InsertQuery;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.model.query.impl.*;
import pl.piotrsukiennik.tuner.model.schema.Table;
import pl.piotrsukiennik.tuner.service.QueryInvalidatonService;
import pl.piotrsukiennik.tuner.service.impl.cache.SupportingQueryHolder;

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
class QueryInvalidatonServiceImpl implements QueryInvalidatonService {

    @Autowired
    private SupportingQueryHolder<ReadQuery> supportingQueryHolder;

    @Override
    public void putCachedQuery( ReadQuery query ) {
        supportingQueryHolder.putQuery( query );
    }

    @Override
    public Collection<ReadQuery> invalidates( InsertQuery insertQuery ) {
        return supportingQueryHolder.getQueriesInvalidatedBy( insertQuery.getTable() );
    }

    @Override
    public Collection<ReadQuery> invalidates( DeleteQuery deleteQuery ) {
        return supportingQueryHolder.getQueriesInvalidatedBy( deleteQuery.getTableSource().getTable() );
    }

    @Override
    public Collection<ReadQuery> invalidates( UpdateQuery updateQuery ) {
        Collection<ReadQuery> queriesToInvalidate = new LinkedHashSet<ReadQuery>();
        if ( updateQuery.getColumnValues() != null ) {
            Set<Table> tablesToStarInvalidate = new LinkedHashSet<Table>();
            for ( ColumnValue columnValue : updateQuery.getColumnValues() ) {
                tablesToStarInvalidate.add( columnValue.getColumn().getTable() );
                queriesToInvalidate.addAll( supportingQueryHolder.getQueriesInvalidatedBy( columnValue.getColumn() ) );
            }
            for ( Table table : tablesToStarInvalidate ) {
                queriesToInvalidate.addAll( supportingQueryHolder.getQueriesInvalidatedByStar( table ) );
            }
            return queriesToInvalidate;
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public Collection<ReadQuery> invalidates( AlterTableQuery alterTableQuery ) {
        return supportingQueryHolder.getQueriesInvalidatedBy( alterTableQuery.getTable() );
    }

    @Override
    public Collection<ReadQuery> invalidates( CreateTableQuery createTableQuery ) {
        return supportingQueryHolder.getQueriesInvalidatedBy( createTableQuery.getTable() );
    }

    @Override
    public Collection<ReadQuery> invalidates( TruncateQuery truncateQuery ) {
        return supportingQueryHolder.getQueriesInvalidatedBy( truncateQuery.getTable() );
    }

    @Override
    public Collection<ReadQuery> invalidates( DropTableQuery dropTableQuery ) {
        return supportingQueryHolder.getQueriesInvalidatedBy( dropTableQuery.getTable() );
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
