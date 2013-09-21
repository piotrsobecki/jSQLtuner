package pl.piotrsukiennik.tuner.util;

import com.sun.rowset.CachedRowSetImpl;

import javax.sql.rowset.CachedRowSet;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Author: Piotr Sukiennik
 * Date: 28.07.13
 * Time: 14:15
 */
public class RowSet {
    private RowSet(){}
    public static CachedRowSetImpl cached(java.sql.ResultSet rowSet) throws SQLException{
        CachedRowSetImpl cachedRowSet = new FixedCachedRowSetImpl();
        cachedRowSet.populate(rowSet);
        return cachedRowSet;
    }


    public static int size(CachedRowSet rowSet){
        return rowSet.size();
    }

}
