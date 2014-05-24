package pl.piotrsukiennik.tuner.service;

import javax.sql.rowset.CachedRowSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * @author Piotr Sukiennik
 * @date 22.05.14
 */
public interface RowSetSizeService {
    int sizeof(CachedRowSet rowSet)  throws SQLException;
    int sizeof(ResultSetMetaData metaData) throws SQLException;
}
