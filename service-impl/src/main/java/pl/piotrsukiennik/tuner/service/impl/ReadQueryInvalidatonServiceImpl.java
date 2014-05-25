package pl.piotrsukiennik.tuner.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.piotrsukiennik.tuner.model.other.ColumnValue;
import pl.piotrsukiennik.tuner.model.query.InsertQuery;
import pl.piotrsukiennik.tuner.model.query.Query;
import pl.piotrsukiennik.tuner.model.query.ReadQuery;
import pl.piotrsukiennik.tuner.model.query.impl.*;
import pl.piotrsukiennik.tuner.model.schema.Table;
import pl.piotrsukiennik.tuner.service.ReadQueryInvalidatonService;
import pl.piotrsukiennik.tuner.service.ReadQueryTreeService;

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
class ReadQueryInvalidatonServiceImpl implements ReadQueryInvalidatonService<ReadQuery> {

    @Autowired
    private ReadQueryTreeService<ReadQuery> readQueryTreeService;

    @Override
    public Collection<ReadQuery> getQueriesInvalidatedBy( Query query ) {
        return query.accept( this );
    }

    @Override
    public void submitInvalidatableReadQuery( ReadQuery query ) {
        readQueryTreeService.submitReadQuery( query );
    }

    @Override
    public Collection<ReadQuery> visit( InsertQuery insertQuery ) {
        return readQueryTreeService.getReadQueries( insertQuery.getTable() );
    }

    @Override
    public Collection<ReadQuery> visit( DeleteQuery deleteQuery ) {
        return readQueryTreeService.getReadQueries( deleteQuery.getTableSource().getTable() );
    }

    @Override
    public Collection<ReadQuery> visit( UpdateQuery updateQuery ) {
        Collection<ReadQuery> queriesToInvalidate = new LinkedHashSet<ReadQuery>();
        if ( updateQuery.getColumnValues() != null ) {
            Set<Table> tablesToStarInvalidate = new LinkedHashSet<Table>();
            for ( ColumnValue columnValue : updateQuery.getColumnValues() ) {
                tablesToStarInvalidate.add( columnValue.getColumn().getTable() );
                queriesToInvalidate.addAll( readQueryTreeService.getReadQueries( columnValue.getColumn() ) );
            }
            for ( Table table : tablesToStarInvalidate ) {
                queriesToInvalidate.addAll( readQueryTreeService.getReadQueriesByStar( table ) );
            }
        }
        return queriesToInvalidate;
    }

    @Override
    public Collection<ReadQuery> visit( AlterTableQuery alterTableQuery ) {
        return readQueryTreeService.getReadQueries( alterTableQuery.getTable() );
    }

    @Override
    public Collection<ReadQuery> visit( CreateTableQuery createTableQuery ) {
        return readQueryTreeService.getReadQueries( createTableQuery.getTable() );
    }

    @Override
    public Collection<ReadQuery> visit( TruncateQuery truncateQuery ) {
        return readQueryTreeService.getReadQueries( truncateQuery.getTable() );
    }

    @Override
    public Collection<ReadQuery> visit( DropTableQuery dropTableQuery ) {
        return readQueryTreeService.getReadQueries( dropTableQuery.getTable() );
    }

    @Override
    public Collection<ReadQuery> visit( SelectQuery selectQuery ) {
        return Collections.EMPTY_LIST;
    }


    @Override
    public Collection<ReadQuery> visit( CreateViewQuery createViewQuery ) {
        return Collections.EMPTY_LIST;
    }

    @Override
    public Collection<ReadQuery> visit( CreateIndexQuery createIndexQuery ) {
        return Collections.EMPTY_LIST;
    }

    @Override
    public Collection<ReadQuery> visit( CallQuery callQuery ) {
        return Collections.EMPTY_LIST;
    }

    @Override
    public Collection<ReadQuery> visit( GenericDropQuery genericDropQuery ) {
        return Collections.EMPTY_LIST;
    }

    @Override
    public Collection<ReadQuery> visit( ReplaceQuery replaceQuery ) {
        return Collections.EMPTY_LIST;
    }

}
