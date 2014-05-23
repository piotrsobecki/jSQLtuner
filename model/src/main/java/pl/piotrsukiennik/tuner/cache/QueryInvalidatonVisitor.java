package pl.piotrsukiennik.tuner.cache;

import pl.piotrsukiennik.tuner.model.query.InsertQuery;
import pl.piotrsukiennik.tuner.model.query.impl.*;

/**
 * @author Piotr Sukiennik
 * @date 28.02.14
 */
public interface QueryInvalidatonVisitor<R> {

    R visit( AlterTableQuery alterTableQuery );

    R visit( CreateViewQuery createViewQuery );

    R visit( CreateTableQuery createTableQuery );

    R visit( CreateIndexQuery createIndexQuery );

    R visit( CallQuery callQuery );

    R visit( TruncateQuery truncateQuery );

    R visit( DropTableQuery dropTableQuery );

    R visit( GenericDropQuery genericDropQuery );

    R visit( ReplaceQuery replaceQuery );

    R visit( SelectQuery selectQuery );

    R visit( InsertQuery insertQuery );

    R visit( DeleteQuery deleteQuery );

    R visit( UpdateQuery updateQuery );

}
