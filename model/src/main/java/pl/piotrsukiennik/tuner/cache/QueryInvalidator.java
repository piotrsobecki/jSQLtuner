package pl.piotrsukiennik.tuner.cache;

import pl.piotrsukiennik.tuner.model.query.InsertQuery;
import pl.piotrsukiennik.tuner.model.query.impl.*;

/**
 * @author Piotr Sukiennik
 * @date 28.02.14
 */
public interface QueryInvalidator<R> {

    R invalidates( AlterTableQuery alterTableQuery );

    R invalidates( CreateViewQuery createViewQuery );

    R invalidates( CreateTableQuery createTableQuery );

    R invalidates( CreateIndexQuery createIndexQuery );

    R invalidates( CallQuery callQuery );

    R invalidates( TruncateQuery truncateQuery );

    R invalidates( DropTableQuery dropTableQuery );

    R invalidates( GenericDropQuery genericDropQuery );

    R invalidates( ReplaceQuery replaceQuery );

    R invalidates( SelectQuery selectQuery );

    R invalidates( InsertQuery insertQuery );

    R invalidates( DeleteQuery deleteQuery );

    R invalidates( UpdateQuery updateQuery );

}
