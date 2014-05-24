package com.sun.rowset;

import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;

/**
 * Author: Piotr Sukiennik
 * Date: 28.07.13
 * Time: 14:15
 */
public class CachedRowSets {
    private CachedRowSets() {
    }

    public static CachedRowSet clone( java.sql.ResultSet rowSet ) throws SQLException {
        if ( rowSet instanceof CachedRowSet ) {
            return CachedRowSets.clone( (CachedRowSet) rowSet );
        }
        else {
            return CachedRowSets.cached( rowSet );
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

}
