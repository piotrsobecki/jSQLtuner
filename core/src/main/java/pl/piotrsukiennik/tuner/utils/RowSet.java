package pl.piotrsukiennik.tuner.utils;

import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;

/**
 * Author: Piotr Sukiennik
 * Date: 28.07.13
 * Time: 14:15
 */
public class RowSet {
    private RowSet() {
    }


    public static CachedRowSet clone( java.sql.ResultSet rowSet ) throws SQLException {
        if ( rowSet instanceof CachedRowSet ) {
            return RowSet.clone( (CachedRowSet) rowSet );
        }
        else {
            return RowSet.cached( rowSet );
        }
    }

    public static CachedRowSet clone( CachedRowSet rowSet ) {
        CachedRowSet cachedRowSet = null;
        try {
            cachedRowSet = cached( rowSet );
            rowSet.beforeFirst();
        }
        catch ( SQLException e ) {
            throw new RuntimeException( e );
        }
        return cachedRowSet;
    }

    public static CachedRowSet cached( java.sql.ResultSet rowSet ) throws SQLException {
        CachedRowSet cachedRowSet = new FixedCachedRowSetImpl();
        cachedRowSet.populate( rowSet );
        return cachedRowSet;
    }


    public static int size( CachedRowSet rowSet ) {
        return rowSet.size();
    }

}
