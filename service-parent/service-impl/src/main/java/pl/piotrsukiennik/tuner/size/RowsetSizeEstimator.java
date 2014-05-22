package pl.piotrsukiennik.tuner.size;

import javax.sql.RowSet;
import javax.sql.RowSetMetaData;
import javax.sql.rowset.CachedRowSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * @author Piotr Sukiennik
 * @date 22.05.14
 */
public interface RowSetSizeEstimator {
    int sizeof(CachedRowSet rowSet)  throws SQLException;
    int sizeof(ResultSetMetaData metaData) throws SQLException;
}
