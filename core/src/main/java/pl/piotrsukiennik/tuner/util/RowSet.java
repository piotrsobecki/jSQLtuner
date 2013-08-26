package pl.piotrsukiennik.tuner.util;

import com.sun.rowset.CachedRowSetImpl;

import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;

/**
 * Author: Piotr Sukiennik
 * Date: 28.07.13
 * Time: 14:15
 */
public class RowSet {
    private RowSet(){}
    public static CachedRowSet cached(java.sql.ResultSet rowSet) throws SQLException{
        CachedRowSetImpl cachedRowSet = new CachedRowSetImpl();
        cachedRowSet.populate(rowSet);

        return cachedRowSet;
    }
}
